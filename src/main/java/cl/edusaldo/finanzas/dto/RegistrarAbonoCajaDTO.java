package cl.edusaldo.finanzas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class RegistrarAbonoCajaDTO {

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    private Long monto;

    @NotBlank(message = "El folio de abono es obligatorio")
    private String abonoFolio;

    @NotNull(message = "El ID del operador es obligatorio")
    private Long usuarioId;

    @NotBlank(message = "La llave de idempotencia es obligatoria")
    private String idempotencyKey;

    public RegistrarAbonoCajaDTO() {}

    public Long getMonto() { return monto; }
    public void setMonto(Long monto) { this.monto = monto; }
    public String getAbonoFolio() { return abonoFolio; }
    public void setAbonoFolio(String abonoFolio) { this.abonoFolio = abonoFolio; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public String getIdempotencyKey() { return idempotencyKey; }
    public void setIdempotencyKey(String idempotencyKey) { this.idempotencyKey = idempotencyKey; }
}
