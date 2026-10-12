package cl.edusaldo.finanzas.controller;

import cl.edusaldo.finanzas.dto.*;
import cl.edusaldo.finanzas.model.*;
import cl.edusaldo.finanzas.service.TesoreríaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finanzas")
@Tag(name = "Finanzas y Tesorería", description = "Cuenta Librería, cartola de caja, resultados (HU58) y doble firma de pagos")
public class TesoreríaController {

    private final TesoreríaService tesoreríaService;

    public TesoreríaController(TesoreríaService tesoreríaService) {
        this.tesoreríaService = tesoreríaService;
    }

    @PostMapping("/caja/abono")
    @Operation(summary = "Registrar ingreso a caja por abono Webpay de alumno (HU58)")
    public ResponseEntity<MovimientoCaja> registrarAbono(@Valid @RequestBody RegistrarAbonoCajaDTO dto) {
        MovimientoCaja mov = tesoreríaService.registrarAbonoCaja(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mov);
    }

    @PostMapping("/resultados/operacion")
    @Operation(summary = "Registrar resultado y margen de venta o devolución sin mover caja (HU58)")
    public ResponseEntity<RegistroResultado> registrarResultado(@Valid @RequestBody RegistrarResultadoDTO dto) {
        RegistroResultado res = tesoreríaService.registrarResultado(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PostMapping("/pagos/solicitar")
    @Operation(summary = "Solicitar pago de factura a proveedor (Admin)")
    public ResponseEntity<SolicitudPago> solicitarPago(@Valid @RequestBody SolicitudPagoDTO dto) {
        SolicitudPago sol = tesoreríaService.solicitarPagoFactura(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(sol);
    }

    @PutMapping("/pagos/{solicitudId}/autorizar")
    @Operation(summary = "Autorizar o rechazar pago de factura (Autorizador - Segunda firma)")
    public ResponseEntity<SolicitudPago> autorizarPago(
            @PathVariable Long solicitudId,
            @Valid @RequestBody AutorizarPagoDTO dto) {
        SolicitudPago sol = tesoreríaService.autorizarPago(solicitudId, dto);
        return ResponseEntity.ok(sol);
    }

    @PostMapping("/pagos/egreso")
    @Operation(summary = "Registrar pago aprobado y egreso automático en Cuenta Librería")
    public ResponseEntity<Pago> registrarEgresoPago(@Valid @RequestBody RegistrarEgresoPagoDTO dto) {
        Pago p = tesoreríaService.registrarEgresoPago(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(p);
    }

    @GetMapping("/caja/movimientos")
    @Operation(summary = "Consultar cartola de movimientos de dinero real en caja")
    public ResponseEntity<List<MovimientoCaja>> listarCaja() {
        return ResponseEntity.ok(tesoreríaService.listarMovimientosCaja());
    }

    @GetMapping("/resultados")
    @Operation(summary = "Consultar registro de márgenes y resultados operativos")
    public ResponseEntity<List<RegistroResultado>> listarResultados() {
        return ResponseEntity.ok(tesoreríaService.listarResultados());
    }
}
