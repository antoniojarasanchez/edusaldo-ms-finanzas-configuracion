package cl.edusaldo.finanzas.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "facturas_por_pagar")
public class FacturaPorPagar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "factura_id", nullable = false)
    private Long facturaId; // Ref escalar a MS Compras

    @Column(name = "orden_compra_folio", nullable = false, length = 50)
    private String ordenCompraFolio;

    @Column(name = "proveedor_id", nullable = false)
    private Long proveedorId; // Ref escalar a MS Compras

    @Column(name = "nombre_proveedor", nullable = false, length = 150)
    private String nombreProveedor;

    @Column(name = "rut_proveedor", nullable = false, length = 12)
    private String rutProveedor;

    @Column(name = "datos_bancarios_proveedor", nullable = false)
    private String datosBancariosProveedor;

    @Column(name = "numero_factura", nullable = false, length = 50)
    private String numeroFactura;

    @Column(name = "fecha_factura", nullable = false)
    private LocalDate fechaFactura;

    @Column(name = "monto_total", nullable = false)
    private Long montoTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 30)
    private EstadoFacturaPago estado = EstadoFacturaPago.PENDIENTE_PAGO;

    @OneToMany(mappedBy = "facturaPorPagar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemFacturaPorPagar> items = new ArrayList<>();

    @OneToMany(mappedBy = "facturaPorPagar")
    private List<SolicitudPago> solicitudes = new ArrayList<>();

    public FacturaPorPagar() {}

    public FacturaPorPagar(Long facturaId, String ordenCompraFolio, Long proveedorId, String nombreProveedor,
                           String rutProveedor, String datosBancariosProveedor, String numeroFactura,
                           LocalDate fechaFactura, Long montoTotal) {
        this.facturaId = facturaId;
        this.ordenCompraFolio = ordenCompraFolio;
        this.proveedorId = proveedorId;
        this.nombreProveedor = nombreProveedor;
        this.rutProveedor = rutProveedor;
        this.datosBancariosProveedor = datosBancariosProveedor;
        this.numeroFactura = numeroFactura;
        this.fechaFactura = fechaFactura;
        this.montoTotal = montoTotal;
        this.estado = EstadoFacturaPago.PENDIENTE_PAGO;
    }

    public void agregarItem(Long productoId, String nombreProducto, String sku, int cantidadRecibida, Long costoUnitarioTotal) {
        this.items.add(new ItemFacturaPorPagar(this, productoId, nombreProducto, sku, cantidadRecibida, costoUnitarioTotal));
    }

    public void solicitarPago() {
        this.estado = EstadoFacturaPago.PAGO_SOLICITADO;
    }

    public void rechazarPago() {
        this.estado = EstadoFacturaPago.PAGO_RECHAZADO;
    }

    public void marcarPagada() {
        this.estado = EstadoFacturaPago.PAGADA;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getFacturaId() { return facturaId; }
    public String getOrdenCompraFolio() { return ordenCompraFolio; }
    public Long getProveedorId() { return proveedorId; }
    public String getNombreProveedor() { return nombreProveedor; }
    public String getRutProveedor() { return rutProveedor; }
    public String getDatosBancariosProveedor() { return datosBancariosProveedor; }
    public String getNumeroFactura() { return numeroFactura; }
    public LocalDate getFechaFactura() { return fechaFactura; }
    public Long getMontoTotal() { return montoTotal; }
    public EstadoFacturaPago getEstado() { return estado; }
    public List<ItemFacturaPorPagar> getItems() { return items; }
}
