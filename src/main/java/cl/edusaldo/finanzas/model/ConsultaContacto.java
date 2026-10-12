package cl.edusaldo.finanzas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "consultas_contacto")
public class ConsultaContacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "primer_nombre", nullable = false, length = 100)
    private String primerNombre;

    @Column(name = "segundo_nombre", length = 100)
    private String segundoNombre;

    @Column(name = "primer_apellido", nullable = false, length = 100)
    private String primerApellido;

    @Column(name = "segundo_apellido", length = 100)
    private String segundoApellido;

    @Column(name = "correo", nullable = false, length = 150)
    private String correo;

    @Column(name = "mensaje", nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 30)
    private EstadoConsultaContacto estado = EstadoConsultaContacto.PENDIENTE;

    @Column(name = "respondida_por_id")
    private Long respondidaPorId;

    public ConsultaContacto() {}

    public ConsultaContacto(String primerNombre, String primerApellido, String correo, String mensaje) {
        this.primerNombre = primerNombre;
        this.primerApellido = primerApellido;
        this.correo = correo;
        this.mensaje = mensaje;
        this.estado = EstadoConsultaContacto.PENDIENTE;
        this.fecha = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getPrimerNombre() { return primerNombre; }
    public String getPrimerApellido() { return primerApellido; }
    public String getCorreo() { return correo; }
    public String getMensaje() { return mensaje; }
    public LocalDateTime getFecha() { return fecha; }
    public EstadoConsultaContacto getEstado() { return estado; }
}
