package cl.edusaldo.finanzas.dto;

import jakarta.validation.constraints.NotNull;

public class AutorizarPagoDTO {

    @NotNull(message = "El ID del autorizador es obligatorio")
    private Long autorizadorId;

    @NotNull(message = "La decisión de aprobación es obligatoria")
    private Boolean aprobada;

    private String motivo;

    public AutorizarPagoDTO() {}

    public Long getAutorizadorId() { return autorizadorId; }
    public void setAutorizadorId(Long autorizadorId) { this.autorizadorId = autorizadorId; }
    public Boolean getAprobada() { return aprobada; }
    public void setAprobada(Boolean aprobada) { this.aprobada = aprobada; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}
