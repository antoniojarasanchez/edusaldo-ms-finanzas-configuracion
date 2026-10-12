package cl.edusaldo.finanzas.service;

import cl.edusaldo.finanzas.model.EstadoPeriodo;
import cl.edusaldo.finanzas.model.PeriodoContable;
import cl.edusaldo.finanzas.repository.PeriodoContableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PeriodoContableService {

    private final PeriodoContableRepository periodoContableRepository;

    public PeriodoContableService(PeriodoContableRepository periodoContableRepository) {
        this.periodoContableRepository = periodoContableRepository;
    }

    @Transactional
    public PeriodoContable abrirPeriodo(int anio, int mes) {
        return periodoContableRepository.findByAnioAndMes(anio, mes)
                .orElseGet(() -> periodoContableRepository.save(new PeriodoContable(anio, mes)));
    }

    @Transactional
    public PeriodoContable pasarARevision(Long id) {
        PeriodoContable p = periodoContableRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Período no encontrado con ID: " + id));
        p.pasarARevision(LocalDateTime.now());
        return periodoContableRepository.save(p);
    }

    @Transactional
    public PeriodoContable cerrarManualmente(Long id, Long adminId) {
        PeriodoContable p = periodoContableRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Período no encontrado con ID: " + id));
        p.cerrarManualmente(adminId);
        return periodoContableRepository.save(p);
    }

    @Transactional
    public boolean verificarYCerrarAutomatico(Long id, int diasRevision) {
        PeriodoContable p = periodoContableRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Período no encontrado con ID: " + id));

        if (p.getEstado() == EstadoPeriodo.EN_REVISION && p.isPlazoRevisionVencido(diasRevision)) {
            p.cerrarAutomaticamente();
            periodoContableRepository.save(p);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<PeriodoContable> listarTodos() {
        return periodoContableRepository.findAll();
    }
}
