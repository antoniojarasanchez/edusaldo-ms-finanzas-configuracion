package cl.edusaldo.finanzas.repository;

import cl.edusaldo.finanzas.model.CuentaLibreria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaLibreriaRepository extends JpaRepository<CuentaLibreria, Long> {
}
