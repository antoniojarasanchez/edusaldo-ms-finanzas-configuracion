package cl.edusaldo.finanzas.controller;

import cl.edusaldo.finanzas.model.PeriodoContable;
import cl.edusaldo.finanzas.service.PeriodoContableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/periodos")
@Tag(name = "Períodos Contables", description = "Ciclo mensual ABIERTO -> EN_REVISION -> CERRADO e inmutabilidad")
public class PeriodoContableController {

    private final PeriodoContableService periodoContableService;

    public PeriodoContableController(PeriodoContableService periodoContableService) {
        this.periodoContableService = periodoContableService;
    }

    @PostMapping("/abrir")
    @Operation(summary = "Abrir o consultar período contable mensual")
    public ResponseEntity<PeriodoContable> abrir(@RequestParam int anio, @RequestParam int mes) {
        PeriodoContable p = periodoContableService.abrirPeriodo(anio, mes);
        return ResponseEntity.ok(p);
    }

    @PutMapping("/{id}/revision")
    @Operation(summary = "Pasar período contable a estado EN_REVISION")
    public ResponseEntity<PeriodoContable> pasarARevision(@PathVariable Long id) {
        PeriodoContable p = periodoContableService.pasarARevision(id);
        return ResponseEntity.ok(p);
    }

    @PutMapping("/{id}/cerrar")
    @Operation(summary = "Cerrar definitivamente un período contable (inmutable)")
    public ResponseEntity<PeriodoContable> cerrarManualmente(
            @PathVariable Long id,
            @RequestParam Long adminId) {
        PeriodoContable p = periodoContableService.cerrarManualmente(id, adminId);
        return ResponseEntity.ok(p);
    }

    @GetMapping
    @Operation(summary = "Listar todos los períodos contables")
    public ResponseEntity<List<PeriodoContable>> listarTodos() {
        return ResponseEntity.ok(periodoContableService.listarTodos());
    }
}
