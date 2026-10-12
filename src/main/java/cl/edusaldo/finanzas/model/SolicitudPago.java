package cl.edusaldo.finanzas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes_pago")
public class SolicitudPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_por_pagar_id", nullable = false)
    private FacturaPorPagar facturaPorPagar;

    @Column(name = "monto", nullable = false)
    private Long monto;

    @Column(name = "solicitante_id", nullable = false)
    private Long solicitanteId; // Admin

    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDateTime fechaSolicitud = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 30)
    private EstadoAutorizacion estado = EstadoAutorizacion.PENDIENTE;

    @Column(name = "autorizador_id")
    private Long autorizadorId; // Autorizador (segunda firma)

    @Column(name = "fecha_resolucion")
    private LocalDateTime fechaResolucion;

    @Column(name = "motivo_rechazo")
    private String motivoRechazo;

    public SolicitudPago() {}

    public SolicitudPago(FacturaPorPagar facturaPorPagar, Long monto, Long solicitanteId) {
        this.facturaPorPagar = facturaPorPagar;
        this.monto = monto;
        this.solicitanteId = solicitanteId;
        this.estado = EstadoAutorizacion.PENDIENTE;
        this.fechaSolicitud = LocalDateTime.now();
    }

    public void aprobar(Long autorizadorId) {
        this.estado = EstadoAutorizacion.APROBADA;
        this.autorizadorId = autorizadorId;
        this.fechaResolucion = LocalDateTime.now();
    }

    public void rechazar(Long autorizadorId, String motivo) {
        this.estado = EstadoAutorizacion.RECHAZADA;
        this.autorizadorId = autorizadorId;
        this.motivoRechazo = motivo;
        this.fechaResolucion = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public FacturaPorPagar getFacturaPorPagar() { return facturaPorPagar; }
    public Long getMonto() { return monto; }
    public Long getSolicitanteId() { return solicitanteId; }
    public LocalDateTime getFechaSolicitud() { return fechaSolicitud; }
    public EstadoAutorizacion getEstado() { return estado; }
    public Long getAutorizadorId() { return autorizadorId; }
    public LocalDateTime getFechaResolucion() { return fechaResolucion; }
    public String getMotivoRechazo() { return motivoRechazo; }
}
