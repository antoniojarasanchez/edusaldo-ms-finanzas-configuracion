package cl.edusaldo.finanzas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class RegistrarEgresoPagoDTO {

    @NotNull(message = "El ID de la factura es obligatorio")
    private Long facturaPorPagarId;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    private Long monto;

    @NotBlank(message = "El comprobante de referencia es obligatorio")
    private String comprobanteReferencia;

    @NotNull(message = "El ID del operador es obligatorio")
    private Long registradoPorId;

    @NotBlank(message = "La llave de idempotencia es obligatoria")
    private String idempotencyKey;

    public RegistrarEgresoPagoDTO() {}

    public Long getFacturaPorPagarId() { return facturaPorPagarId; }
    public void setFacturaPorPagarId(Long facturaPorPagarId) { this.facturaPorPagarId = facturaPorPagarId; }
    public Long getMonto() { return monto; }
    public void setMonto(Long monto) { this.monto = monto; }
    public String getComprobanteReferencia() { return comprobanteReferencia; }
    public void setComprobanteReferencia(String comprobanteReferencia) { this.comprobanteReferencia = comprobanteReferencia; }
    public Long getRegistradoPorId() { return registradoPorId; }
    public void setRegistradoPorId(Long registradoPorId) { this.registradoPorId = registradoPorId; }
    public String getIdempotencyKey() { return idempotencyKey; }
    public void setIdempotencyKey(String idempotencyKey) { this.idempotencyKey = idempotencyKey; }
}
