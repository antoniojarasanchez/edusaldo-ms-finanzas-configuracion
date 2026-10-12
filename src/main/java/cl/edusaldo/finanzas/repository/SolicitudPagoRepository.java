package cl.edusaldo.finanzas.repository;

import cl.edusaldo.finanzas.model.EstadoAutorizacion;
import cl.edusaldo.finanzas.model.SolicitudPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudPagoRepository extends JpaRepository<SolicitudPago, Long> {
    List<SolicitudPago> findByEstado(EstadoAutorizacion estado);
}
