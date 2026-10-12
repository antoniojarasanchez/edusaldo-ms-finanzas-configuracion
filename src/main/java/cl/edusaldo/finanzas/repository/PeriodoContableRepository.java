package cl.edusaldo.finanzas.repository;

import cl.edusaldo.finanzas.model.EstadoPeriodo;
import cl.edusaldo.finanzas.model.PeriodoContable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeriodoContableRepository extends JpaRepository<PeriodoContable, Long> {
    Optional<PeriodoContable> findByAnioAndMes(int anio, int mes);
    List<PeriodoContable> findByEstado(EstadoPeriodo estado);
}
