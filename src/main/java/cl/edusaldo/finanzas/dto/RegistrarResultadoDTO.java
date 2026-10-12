package cl.edusaldo.finanzas.dto;

import cl.edusaldo.finanzas.model.TipoOperacionResultado;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class RegistrarResultadoDTO {

    @NotNull(message = "El tipo de operación es obligatorio")
    private TipoOperacionResultado tipo;

    @NotNull(message = "El monto de venta es obligatorio")
    @Positive(message = "El monto de venta debe ser positivo")
    private Long montoVenta;

    @NotNull(message = "El costo de venta es obligatorio")
    @Positive(message = "El costo de venta debe ser positivo")
    private Long costoVenta;

    @NotNull(message = "El ID del alumno es obligatorio")
    private Long alumnoId;

    @NotNull(message = "El ID de referencia es obligatorio")
    private Long referenciaId;

    @NotBlank(message = "El folio de referencia es obligatorio")
    private String referenciaFolio;

    @NotBlank(message = "La llave de idempotencia es obligatoria")
    private String idempotencyKey;

    public RegistrarResultadoDTO() {}

    public TipoOperacionResultado getTipo() { return tipo; }
    public void setTipo(TipoOperacionResultado tipo) { this.tipo = tipo; }
    public Long getMontoVenta() { return montoVenta; }
    public void setMontoVenta(Long montoVenta) { this.montoVenta = montoVenta; }
    public Long getCostoVenta() { return costoVenta; }
    public void setCostoVenta(Long costoVenta) { this.costoVenta = costoVenta; }
    public Long getAlumnoId() { return alumnoId; }
    public void setAlumnoId(Long alumnoId) { this.alumnoId = alumnoId; }
    public Long getReferenciaId() { return referenciaId; }
    public void setReferenciaId(Long referenciaId) { this.referenciaId = referenciaId; }
    public String getReferenciaFolio() { return referenciaFolio; }
    public void setReferenciaFolio(String referenciaFolio) { this.referenciaFolio = referenciaFolio; }
    public String getIdempotencyKey() { return idempotencyKey; }
    public void setIdempotencyKey(String idempotencyKey) { this.idempotencyKey = idempotencyKey; }
}
