package cl.edusaldo.finanzas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "registros_auditoria")
public class RegistroAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora = LocalDateTime.now();

    @Column(name = "microservicio", nullable = false, length = 50)
    private String microservicio;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "rol", nullable = false, length = 50)
    private String rol;

    @Column(name = "accion", nullable = false, length = 100)
    private String accion;

    @Column(name = "entidad", nullable = false, length = 50)
    private String entidad;

    @Column(name = "entidad_id", nullable = false, length = 50)
    private String entidadId;

    @Column(name = "valor_anterior", columnDefinition = "TEXT")
    private String valorAnterior;

    @Column(name = "valor_nuevo", columnDefinition = "TEXT")
    private String valorNuevo;

    @Column(name = "motivo")
    private String motivo;

    public RegistroAuditoria() {}

    public RegistroAuditoria(String microservicio, Long usuarioId, String rol, String accion, String entidad, String entidadId, String motivo) {
        this.microservicio = microservicio;
        this.usuarioId = usuarioId;
        this.rol = rol;
        this.accion = accion;
        this.entidad = entidad;
        this.entidadId = entidadId;
        this.motivo = motivo;
        this.fechaHora = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public String getMicroservicio() { return microservicio; }
    public Long getUsuarioId() { return usuarioId; }
    public String getRol() { return rol; }
    public String getAccion() { return accion; }
    public String getEntidad() { return entidad; }
    public String getEntidadId() { return entidadId; }
    public String getValorAnterior() { return valorAnterior; }
    public void setValorAnterior(String valorAnterior) { this.valorAnterior = valorAnterior; }
    public String getValorNuevo() { return valorNuevo; }
    public void setValorNuevo(String valorNuevo) { this.valorNuevo = valorNuevo; }
    public String getMotivo() { return motivo; }
}
