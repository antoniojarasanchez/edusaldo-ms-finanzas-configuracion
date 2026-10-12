package cl.edusaldo.finanzas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "factura_por_pagar_id", nullable = false)
    private Long facturaPorPagarId;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago = LocalDateTime.now();

    @Column(name = "monto", nullable = false)
    private Long monto;

    @Column(name = "medio_pago", nullable = false, length = 50)
    private String medioPago = "TRANSFERENCIA";

    @Column(name = "comprobante_referencia", nullable = false, length = 100)
    private String comprobanteReferencia;

    @Column(name = "registrado_por_id", nullable = false)
    private Long registradoPorId;

    public Pago() {}

    public Pago(Long facturaPorPagarId, Long monto, String medioPago, String comprobanteReferencia, Long registradoPorId) {
        this.facturaPorPagarId = facturaPorPagarId;
        this.monto = monto;
        this.medioPago = medioPago;
        this.comprobanteReferencia = comprobanteReferencia;
        this.registradoPorId = registradoPorId;
        this.fechaPago = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getFacturaPorPagarId() { return facturaPorPagarId; }
    public LocalDateTime getFechaPago() { return fechaPago; }
    public Long getMonto() { return monto; }
    public String getMedioPago() { return medioPago; }
    public String getComprobanteReferencia() { return comprobanteReferencia; }
    public Long getRegistradoPorId() { return registradoPorId; }
}
