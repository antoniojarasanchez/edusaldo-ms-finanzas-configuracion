package cl.edusaldo.finanzas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimientos_caja")
public class MovimientoCaja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_libreria_id", nullable = false)
    @JsonIgnore
    private CuentaLibreria cuentaLibreria;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "naturaleza", nullable = false, length = 20)
    private NaturalezaMovimiento naturaleza;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 50)
    private TipoMovimientoCaja tipo;

    @Column(name = "concepto", nullable = false)
    private String concepto;

    @Column(name = "monto", nullable = false)
    private Long monto;

    @Column(name = "saldo_resultante", nullable = false)
    private Long saldoResultante;

    @Column(name = "referencia_tipo", nullable = false, length = 50)
    private String referenciaTipo;

    @Column(name = "referencia_id", nullable = false, length = 100)
    private String referenciaId;

    @Column(name = "idempotency_key", nullable = false, unique = true, length = 100)
    private String idempotencyKey;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId; // Ref escalar a MS Auth

    public MovimientoCaja() {}

    public MovimientoCaja(CuentaLibreria cuentaLibreria, NaturalezaMovimiento naturaleza, TipoMovimientoCaja tipo,
                          String concepto, Long monto, Long saldoResultante, String referenciaTipo,
                          String referenciaId, String idempotencyKey, Long usuarioId) {
        this.cuentaLibreria = cuentaLibreria;
        this.naturaleza = naturaleza;
        this.tipo = tipo;
        this.concepto = concepto;
        this.monto = monto;
        this.saldoResultante = saldoResultante;
        this.referenciaTipo = referenciaTipo;
        this.referenciaId = referenciaId;
        this.idempotencyKey = idempotencyKey;
        this.usuarioId = usuarioId;
        this.fecha = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public CuentaLibreria getCuentaLibreria() { return cuentaLibreria; }
    public LocalDateTime getFecha() { return fecha; }
    public NaturalezaMovimiento getNaturaleza() { return naturaleza; }
    public TipoMovimientoCaja getTipo() { return tipo; }
    public String getConcepto() { return concepto; }
    public Long getMonto() { return monto; }
    public Long getSaldoResultante() { return saldoResultante; }
    public String getReferenciaTipo() { return referenciaTipo; }
    public String getReferenciaId() { return referenciaId; }
    public String getIdempotencyKey() { return idempotencyKey; }
    public Long getUsuarioId() { return usuarioId; }
}
