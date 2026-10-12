package cl.edusaldo.finanzas.service;

import cl.edusaldo.finanzas.dto.*;
import cl.edusaldo.finanzas.model.*;
import cl.edusaldo.finanzas.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TesoreríaService {

    private final CuentaLibreriaRepository cuentaLibreriaRepository;
    private final MovimientoCajaRepository movimientoCajaRepository;
    private final RegistroResultadoRepository registroResultadoRepository;
    private final FacturaPorPagarRepository facturaPorPagarRepository;
    private final SolicitudPagoRepository solicitudPagoRepository;
    private final PagoRepository pagoRepository;
    private final PeriodoContableRepository periodoContableRepository;

    public TesoreríaService(CuentaLibreriaRepository cuentaLibreriaRepository,
                            MovimientoCajaRepository movimientoCajaRepository,
                            RegistroResultadoRepository registroResultadoRepository,
                            FacturaPorPagarRepository facturaPorPagarRepository,
                            SolicitudPagoRepository solicitudPagoRepository,
                            PagoRepository pagoRepository,
                            PeriodoContableRepository periodoContableRepository) {
        this.cuentaLibreriaRepository = cuentaLibreriaRepository;
        this.movimientoCajaRepository = movimientoCajaRepository;
        this.registroResultadoRepository = registroResultadoRepository;
        this.facturaPorPagarRepository = facturaPorPagarRepository;
        this.solicitudPagoRepository = solicitudPagoRepository;
        this.pagoRepository = pagoRepository;
        this.periodoContableRepository = periodoContableRepository;
    }

    // Regla HU58: Abonos sí mueven caja (ingreso real)
    @Transactional
    public MovimientoCaja registrarAbonoCaja(RegistrarAbonoCajaDTO dto) {
        if (movimientoCajaRepository.existsByIdempotencyKey(dto.getIdempotencyKey())) {
            return movimientoCajaRepository.findByIdempotencyKey(dto.getIdempotencyKey()).get();
        }

        CuentaLibreria cuenta = cuentaLibreriaRepository.findById(1L)
                .orElseGet(() -> cuentaLibreriaRepository.save(new CuentaLibreria(0L)));

        return cuenta.registrarIngreso(
                dto.getMonto(), TipoMovimientoCaja.ABONO, "ABONO_ALUMNO",
                dto.getAbonoFolio(), "Recarga de saldo alumno #" + dto.getAbonoFolio(),
                dto.getUsuarioId(), dto.getIdempotencyKey()
        );
    }

    // Regla HU58: Ventas y Devoluciones NO mueven caja, mueven resultado
    @Transactional
    public RegistroResultado registrarResultado(RegistrarResultadoDTO dto) {
        if (registroResultadoRepository.existsByIdempotencyKey(dto.getIdempotencyKey())) {
            return registroResultadoRepository.findByIdempotencyKey(dto.getIdempotencyKey()).get();
        }

        RegistroResultado res = new RegistroResultado(
                dto.getTipo(), dto.getMontoVenta(), dto.getCostoVenta(), dto.getAlumnoId(),
                dto.getReferenciaId(), dto.getReferenciaFolio(), dto.getIdempotencyKey()
        );
        return registroResultadoRepository.save(res);
    }

    // Doble firma: Admin solicita pago
    @Transactional
    public SolicitudPago solicitarPagoFactura(SolicitudPagoDTO dto) {
        FacturaPorPagar fpp = facturaPorPagarRepository.findById(dto.getFacturaPorPagarId())
                .orElseThrow(() -> new IllegalArgumentException("Factura por pagar no encontrada con ID: " + dto.getFacturaPorPagarId()));

        if (fpp.getEstado() == EstadoFacturaPago.PAGADA) {
            throw new IllegalStateException("La factura ya se encuentra pagada.");
        }

        fpp.solicitarPago();
        facturaPorPagarRepository.save(fpp);

        SolicitudPago sol = new SolicitudPago(fpp, dto.getMonto(), dto.getSolicitanteId());
        return solicitudPagoRepository.save(sol);
    }

    // Doble firma: Autorizador aprueba o rechaza
    @Transactional
    public SolicitudPago autorizarPago(Long solicitudId, AutorizarPagoDTO dto) {
        SolicitudPago sol = solicitudPagoRepository.findById(solicitudId)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud de pago no encontrada con ID: " + solicitudId));

        if (sol.getEstado() != EstadoAutorizacion.PENDIENTE) {
            throw new IllegalStateException("La solicitud de pago ya fue resuelta previamente.");
        }

        if (Boolean.TRUE.equals(dto.getAprobada())) {
            sol.aprobar(dto.getAutorizadorId());
        } else {
            sol.rechazar(dto.getAutorizadorId(), dto.getMotivo());
            sol.getFacturaPorPagar().rechazarPago();
            facturaPorPagarRepository.save(sol.getFacturaPorPagar());
        }

        return solicitudPagoRepository.save(sol);
    }

    // Doble firma: Admin registra el egreso efectivo tras aprobación
    @Transactional
    public Pago registrarEgresoPago(RegistrarEgresoPagoDTO dto) {
        FacturaPorPagar fpp = facturaPorPagarRepository.findById(dto.getFacturaPorPagarId())
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada con ID: " + dto.getFacturaPorPagarId()));

        if (fpp.getEstado() == EstadoFacturaPago.PAGADA) {
            throw new IllegalStateException("La factura ya fue pagada.");
        }

        fpp.marcarPagada();
        facturaPorPagarRepository.save(fpp);

        Pago pago = new Pago(fpp.getId(), dto.getMonto(), "TRANSFERENCIA", dto.getComprobanteReferencia(), dto.getRegistradoPorId());
        pagoRepository.save(pago);

        CuentaLibreria cuenta = cuentaLibreriaRepository.findById(1L)
                .orElseGet(() -> cuentaLibreriaRepository.save(new CuentaLibreria(0L)));

        cuenta.registrarEgreso(
                dto.getMonto(), TipoMovimientoCaja.PAGO_FACTURA, "FACTURA_PROVEEDOR",
                fpp.getNumeroFactura(), "Pago factura proveedor #" + fpp.getNumeroFactura() + " - " + fpp.getNombreProveedor(),
                dto.getRegistradoPorId(), dto.getIdempotencyKey()
        );

        return pago;
    }

    @Transactional(readOnly = true)
    public List<MovimientoCaja> listarMovimientosCaja() {
        return movimientoCajaRepository.findAllByOrderByFechaDesc();
    }

    @Transactional(readOnly = true)
    public List<RegistroResultado> listarResultados() {
        return registroResultadoRepository.findAllByOrderByFechaDesc();
    }
}
