package cl.edusaldo.finanzas;

import cl.edusaldo.finanzas.dto.*;
import cl.edusaldo.finanzas.model.*;
import cl.edusaldo.finanzas.repository.*;
import cl.edusaldo.finanzas.service.TesoreríaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TesoreríaServiceTest {

    @Mock
    private CuentaLibreriaRepository cuentaLibreriaRepository;

    @Mock
    private MovimientoCajaRepository movimientoCajaRepository;

    @Mock
    private RegistroResultadoRepository registroResultadoRepository;

    @Mock
    private FacturaPorPagarRepository facturaPorPagarRepository;

    @Mock
    private SolicitudPagoRepository solicitudPagoRepository;

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private PeriodoContableRepository periodoContableRepository;

    @InjectMocks
    private TesoreríaService tesoreríaService;

    private CuentaLibreria cuentaLibreria;
    private FacturaPorPagar facturaPrueba;

    @BeforeEach
    void setUp() {
        cuentaLibreria = new CuentaLibreria(500000L); // Saldo inicial caja $500.000

        facturaPrueba = new FacturaPorPagar(
                50L, "OC-1001", 1L, "Distribuidora Aconcagua S.A.",
                "76.123.456-7", "Banco de Chile Cta Cte 12345", "FAC-882",
                LocalDate.now(), 11900L
        );
        facturaPrueba.setId(10L);
    }

    @Test
    @DisplayName("1. Regla HU58: Venta en librería calcula margen y NO genera movimiento de caja")
    void testReglaHU58VentaNoTocaCaja() {
        when(registroResultadoRepository.existsByIdempotencyKey("KEY-RES-01")).thenReturn(false);
        when(registroResultadoRepository.save(any(RegistroResultado.class))).thenAnswer(i -> i.getArgument(0));

        RegistrarResultadoDTO dto = new RegistrarResultadoDTO();
        dto.setTipo(TipoOperacionResultado.VENTA);
        dto.setMontoVenta(2500L);
        dto.setCostoVenta(1900L); // cant * CPP con IVA
        dto.setAlumnoId(1L);
        dto.setReferenciaId(101L);
        dto.setReferenciaFolio("VTA-001");
        dto.setIdempotencyKey("KEY-RES-01");

        RegistroResultado res = tesoreríaService.registrarResultado(dto);

        assertNotNull(res);
        assertEquals(2500L, res.getMontoVenta());
        assertEquals(1900L, res.getCostoVenta());
        // Margen bruto exacto = 2500 - 1900 = $600
        assertEquals(600L, res.getMargenUtilidad());

        // Aserción estricta de no doble contabilización: caja NUNCA debe ser tocada
        verify(cuentaLibreriaRepository, never()).save(any());
        verify(movimientoCajaRepository, never()).save(any());
    }

    @Test
    @DisplayName("2. Regla HU58: Abono Webpay registra ingreso real de dinero en Cuenta Librería")
    void testAbonoRegistraIngresoRealEnCaja() {
        when(movimientoCajaRepository.existsByIdempotencyKey("KEY-ABONO-01")).thenReturn(false);
        when(cuentaLibreriaRepository.findById(1L)).thenReturn(Optional.of(cuentaLibreria));

        RegistrarAbonoCajaDTO dto = new RegistrarAbonoCajaDTO();
        dto.setMonto(20000L);
        dto.setAbonoFolio("ABO-501");
        dto.setUsuarioId(4L);
        dto.setIdempotencyKey("KEY-ABONO-01");

        MovimientoCaja mov = tesoreríaService.registrarAbonoCaja(dto);

        assertNotNull(mov);
        assertEquals(NaturalezaMovimiento.INGRESO, mov.getNaturaleza());
        assertEquals(TipoMovimientoCaja.ABONO, mov.getTipo());
        assertEquals(20000L, mov.getMonto());
        // Saldo resultante en caja sube de 500.000 a 520.000
        assertEquals(520000L, cuentaLibreria.getSaldoActual());
        assertEquals(520000L, mov.getSaldoResultante());
    }

    @Test
    @DisplayName("3. Doble firma: Pago de factura exige solicitud y aprobación previa antes del egreso")
    void testPagoFacturaExigeDobleFirma() {
        when(facturaPorPagarRepository.findById(10L)).thenReturn(Optional.of(facturaPrueba));
        when(solicitudPagoRepository.save(any(SolicitudPago.class))).thenAnswer(i -> {
            SolicitudPago s = i.getArgument(0);
            return s;
        });

        // Paso 1: Admin solicita pago
        SolicitudPagoDTO dtoSol = new SolicitudPagoDTO();
        dtoSol.setFacturaPorPagarId(10L);
        dtoSol.setSolicitanteId(1L);
        dtoSol.setMonto(11900L);

        SolicitudPago sol = tesoreríaService.solicitarPagoFactura(dtoSol);
        assertEquals(EstadoAutorizacion.PENDIENTE, sol.getEstado());
        assertEquals(EstadoFacturaPago.PAGO_SOLICITADO, facturaPrueba.getEstado());

        // Paso 2: Autorizador aprueba
        when(solicitudPagoRepository.findById(100L)).thenReturn(Optional.of(sol));
        AutorizarPagoDTO dtoAuth = new AutorizarPagoDTO();
        dtoAuth.setAutorizadorId(3L);
        dtoAuth.setAprobada(true);

        SolicitudPago solAprobada = tesoreríaService.autorizarPago(100L, dtoAuth);
        assertEquals(EstadoAutorizacion.APROBADA, solAprobada.getEstado());

        // Paso 3: Admin registra el egreso
        when(cuentaLibreriaRepository.findById(1L)).thenReturn(Optional.of(cuentaLibreria));
        when(pagoRepository.save(any(Pago.class))).thenAnswer(i -> i.getArgument(0));

        RegistrarEgresoPagoDTO dtoEgreso = new RegistrarEgresoPagoDTO();
        dtoEgreso.setFacturaPorPagarId(10L);
        dtoEgreso.setMonto(11900L);
        dtoEgreso.setComprobanteReferencia("TEF-99482");
        dtoEgreso.setRegistradoPorId(1L);
        dtoEgreso.setIdempotencyKey("KEY-PAY-01");

        Pago p = tesoreríaService.registrarEgresoPago(dtoEgreso);
        assertNotNull(p);
        assertEquals(EstadoFacturaPago.PAGADA, facturaPrueba.getEstado());
        // Saldo de caja disminuye por el pago
        assertEquals(500000L - 11900L, cuentaLibreria.getSaldoActual());
    }

    @Test
    @DisplayName("4. Inmutabilidad de período contable cerrado (HU61, HU62)")
    void testInmutabilidadPeriodoCerrado() {
        PeriodoContable periodo = new PeriodoContable(2026, 9);
        assertTrue(periodo.admiteMovimientos());

        periodo.pasarARevision(null);
        assertTrue(periodo.admiteMovimientos());

        periodo.cerrarManualmente(1L);
        assertEquals(EstadoPeriodo.CERRADO, periodo.getEstado());
        // En período cerrado no admite movimientos
        assertFalse(periodo.admiteMovimientos());
    }
}
