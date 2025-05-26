package com.plataforma.ecommerce.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoResponseDTO {

    private Long id;

    @NotNull(message = "El ID del pedido no puede ser nulo")
    private Long pedidoId;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor que cero")
    private BigDecimal monto;

    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago;

    @NotBlank(message = "El estado del pago es obligatorio")
    private String estado;

    @NotNull(message = "La fecha de registro es obligatoria")
    private LocalDateTime fechaRegistro;
}
