package cl.edusaldo.finanzas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "registros_resultado")
public class RegistroResultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 30)
    private TipoOperacionResultado tipo;

    @Column(name = "monto_venta", nullable = false)
    private Long montoVenta;

    @Column(name = "costo_venta", nullable = false)
    private Long costoVenta; // cant * CPP con IVA

    @Column(name = "alumno_id", nullable = false)
    private Long alumnoId;   // Ref escalar a MS Alumnos

    @Column(name = "referencia_id", nullable = false)
    private Long referenciaId; // Ref escalar a MS Ventas

    @Column(name = "referencia_folio", nullable = false, length = 50)
    private String referenciaFolio;

    @Column(name = "idempotency_key", nullable = false, unique = true, length = 100)
    private String idempotencyKey;

    public RegistroResultado() {}

    public RegistroResultado(TipoOperacionResultado tipo, Long montoVenta, Long costoVenta, Long alumnoId,
                             Long referenciaId, String referenciaFolio, String idempotencyKey) {
        this.tipo = tipo;
        this.montoVenta = montoVenta;
        this.costoVenta = costoVenta;
        this.alumnoId = alumnoId;
        this.referenciaId = referenciaId;
        this.referenciaFolio = referenciaFolio;
        this.idempotencyKey = idempotencyKey;
        this.fecha = LocalDateTime.now();
    }

    public Long getMargenUtilidad() {
        return this.montoVenta - this.costoVenta;
    }

    public Long getId() { return id; }
    public LocalDateTime getFecha() { return fecha; }
    public TipoOperacionResultado getTipo() { return tipo; }
    public Long getMontoVenta() { return montoVenta; }
    public Long getCostoVenta() { return costoVenta; }
    public Long getAlumnoId() { return alumnoId; }
    public Long getReferenciaId() { return referenciaId; }
    public String getReferenciaFolio() { return referenciaFolio; }
    public String getIdempotencyKey() { return idempotencyKey; }
}
