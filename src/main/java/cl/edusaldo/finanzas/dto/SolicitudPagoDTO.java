package cl.edusaldo.finanzas.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class SolicitudPagoDTO {

    @NotNull(message = "El ID de la factura por pagar es obligatorio")
    private Long facturaPorPagarId;

    @NotNull(message = "El ID del administrador solicitante es obligatorio")
    private Long solicitanteId;

    @NotNull(message = "El monto a pagar es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    private Long monto;

    public SolicitudPagoDTO() {}

    public Long getFacturaPorPagarId() { return facturaPorPagarId; }
    public void setFacturaPorPagarId(Long facturaPorPagarId) { this.facturaPorPagarId = facturaPorPagarId; }
    public Long getSolicitanteId() { return solicitanteId; }
    public void setSolicitanteId(Long solicitanteId) { this.solicitanteId = solicitanteId; }
    public Long getMonto() { return monto; }
    public void setMonto(Long monto) { this.monto = monto; }
}
