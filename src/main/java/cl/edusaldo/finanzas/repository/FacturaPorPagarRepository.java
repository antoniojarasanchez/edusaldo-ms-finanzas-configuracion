package cl.edusaldo.finanzas.repository;

import cl.edusaldo.finanzas.model.EstadoFacturaPago;
import cl.edusaldo.finanzas.model.FacturaPorPagar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacturaPorPagarRepository extends JpaRepository<FacturaPorPagar, Long> {
    Optional<FacturaPorPagar> findByFacturaId(Long facturaId);
    List<FacturaPorPagar> findByEstado(EstadoFacturaPago estado);
}
