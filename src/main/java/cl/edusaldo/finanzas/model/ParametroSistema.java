package cl.edusaldo.finanzas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "parametros_sistema")
public class ParametroSistema {

    @Id
    @Column(name = "clave", length = 50)
    private String clave;

    @Column(name = "valor", nullable = false)
    private String valor;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "fecha_modificacion", nullable = false)
    private LocalDateTime fechaModificacion = LocalDateTime.now();

    @Column(name = "modificado_por_id", nullable = false)
    private Long modificadoPorId;

    public ParametroSistema() {}

    public ParametroSistema(String clave, String valor, String descripcion, Long modificadoPorId) {
        this.clave = clave;
        this.valor = valor;
        this.descripcion = descripcion;
        this.modificadoPorId = modificadoPorId;
        this.fechaModificacion = LocalDateTime.now();
    }

    public String getClave() { return clave; }
    public String getValor() { return valor; }
    public void setValor(String valor) { this.valor = valor; }
    public String getDescripcion() { return descripcion; }
    public LocalDateTime getFechaModificacion() { return fechaModificacion; }
    public Long getModificadoPorId() { return modificadoPorId; }
}
