package cl.edusaldo.finanzas.repository;

import cl.edusaldo.finanzas.model.RegistroResultado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistroResultadoRepository extends JpaRepository<RegistroResultado, Long> {
    List<RegistroResultado> findAllByOrderByFechaDesc();
    Optional<RegistroResultado> findByIdempotencyKey(String idempotencyKey);
    boolean existsByIdempotencyKey(String idempotencyKey);
}
