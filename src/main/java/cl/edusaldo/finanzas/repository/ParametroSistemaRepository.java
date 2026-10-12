package cl.edusaldo.finanzas.repository;

import cl.edusaldo.finanzas.model.ParametroSistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametroSistemaRepository extends JpaRepository<ParametroSistema, String> {
}
