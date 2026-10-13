package cl.edusaldo.finanzas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "items_factura_por_pagar")
public class ItemFacturaPorPagar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_por_pagar_id", nullable = false)
    @JsonIgnore
    private FacturaPorPagar facturaPorPagar;

    @Column(name = "producto_id", nullable = false)
    private Long productoId;

    @Column(name = "nombre_producto", nullable = false, length = 150)
    private String nombreProducto;

    @Column(name = "sku", nullable = false, length = 50)
    private String sku;

    @Column(name = "cantidad_recibida", nullable = false)
    private int cantidadRecibida;

    @Column(name = "costo_unitario_total", nullable = false)
    private Long costoUnitarioTotal;

    @Column(name = "subtotal", nullable = false)
    private Long subtotal;

    public ItemFacturaPorPagar() {}

    public ItemFacturaPorPagar(FacturaPorPagar facturaPorPagar, Long productoId, String nombreProducto, String sku, int cantidadRecibida, Long costoUnitarioTotal) {
        this.facturaPorPagar = facturaPorPagar;
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.sku = sku;
        this.cantidadRecibida = cantidadRecibida;
        this.costoUnitarioTotal = costoUnitarioTotal;
        this.subtotal = (long) cantidadRecibida * costoUnitarioTotal;
    }

    public Long getId() { return id; }
    public FacturaPorPagar getFacturaPorPagar() { return facturaPorPagar; }
    public Long getProductoId() { return productoId; }
    public String getNombreProducto() { return nombreProducto; }
    public String getSku() { return sku; }
    public int getCantidadRecibida() { return cantidadRecibida; }
    public Long getCostoUnitarioTotal() { return costoUnitarioTotal; }
    public Long getSubtotal() { return subtotal; }
}
