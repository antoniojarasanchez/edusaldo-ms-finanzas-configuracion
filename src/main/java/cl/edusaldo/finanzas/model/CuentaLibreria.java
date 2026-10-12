package cl.edusaldo.finanzas.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cuentas_libreria")
public class CuentaLibreria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "saldo_apertura", nullable = false)
    private Long saldoApertura = 0L;

    @Column(name = "saldo_actual", nullable = false)
    private Long saldoActual = 0L;

    @OneToMany(mappedBy = "cuentaLibreria", cascade = CascadeType.ALL)
    private List<MovimientoCaja> movimientos = new ArrayList<>();

    public CuentaLibreria() {}

    public CuentaLibreria(Long saldoApertura) {
        this.saldoApertura = saldoApertura;
        this.saldoActual = saldoApertura;
    }

    public MovimientoCaja registrarIngreso(Long monto, TipoMovimientoCaja tipo, String refTipo, String refId, String concepto, Long usuarioId, String idempotencyKey) {
        this.saldoActual += monto;
        MovimientoCaja mov = new MovimientoCaja(this, NaturalezaMovimiento.INGRESO, tipo, concepto, monto, this.saldoActual, refTipo, refId, idempotencyKey, usuarioId);
        this.movimientos.add(mov);
        return mov;
    }

    public MovimientoCaja registrarEgreso(Long monto, TipoMovimientoCaja tipo, String refTipo, String refId, String concepto, Long usuarioId, String idempotencyKey) {
        this.saldoActual -= monto;
        MovimientoCaja mov = new MovimientoCaja(this, NaturalezaMovimiento.EGRESO, tipo, concepto, monto, this.saldoActual, refTipo, refId, idempotencyKey, usuarioId);
        this.movimientos.add(mov);
        return mov;
    }

    public Long getId() { return id; }
    public Long getSaldoApertura() { return saldoApertura; }
    public Long getSaldoActual() { return saldoActual; }
    public List<MovimientoCaja> getMovimientos() { return movimientos; }
}
