package cl.edusaldo.finanzas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "periodos_contables", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"anio", "mes"})
})
public class PeriodoContable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "anio", nullable = false)
    private int anio;

    @Column(name = "mes", nullable = false)
    private int mes;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 30)
    private EstadoPeriodo estado = EstadoPeriodo.ABIERTO;

    @Column(name = "fecha_inicio_revision")
    private LocalDateTime fechaInicioRevision;

    @Column(name = "fecha_cierre")
    private LocalDateTime fechaCierre;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cierre", length = 30)
    private TipoCierre tipoCierre;

    @Column(name = "cerrado_por_id")
    private Long cerradoPorId; // Ref escalar a MS Auth

    public PeriodoContable() {}

    public PeriodoContable(int anio, int mes) {
        this.anio = anio;
        this.mes = mes;
        this.estado = EstadoPeriodo.ABIERTO;
    }

    public void pasarARevision(LocalDateTime fechaInicio) {
        this.estado = EstadoPeriodo.EN_REVISION;
        this.fechaInicioRevision = fechaInicio != null ? fechaInicio : LocalDateTime.now();
    }

    public void cerrarManualmente(Long adminId) {
        this.estado = EstadoPeriodo.CERRADO;
        this.tipoCierre = TipoCierre.MANUAL;
        this.cerradoPorId = adminId;
        this.fechaCierre = LocalDateTime.now();
    }

    public void cerrarAutomaticamente() {
        this.estado = EstadoPeriodo.CERRADO;
        this.tipoCierre = TipoCierre.AUTOMATICO;
        this.fechaCierre = LocalDateTime.now();
    }

    public boolean admiteMovimientos() {
        return this.estado != EstadoPeriodo.CERRADO;
    }

    public boolean isPlazoRevisionVencido(int diasRevision) {
        if (this.fechaInicioRevision == null) return false;
        return LocalDateTime.now().isAfter(this.fechaInicioRevision.plusDays(diasRevision));
    }

    public Long getId() { return id; }
    public int getAnio() { return anio; }
    public int getMes() { return mes; }
    public EstadoPeriodo getEstado() { return estado; }
    public LocalDateTime getFechaInicioRevision() { return fechaInicioRevision; }
    public LocalDateTime getFechaCierre() { return fechaCierre; }
    public TipoCierre getTipoCierre() { return tipoCierre; }
    public Long getCerradoPorId() { return cerradoPorId; }
}
