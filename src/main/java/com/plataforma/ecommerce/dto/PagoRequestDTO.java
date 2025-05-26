package com.plataforma.ecommerce.dto;

import com.plataforma.ecommerce.validation.MetodoPagoValido;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoRequestDTO implements Serializable {

    @NotNull(message = "El ID del pedido es obligatorio")
    @Positive(message = "El ID del pedido debe ser positivo")
    private Long pedidoId;

    @NotNull(message = "El monto del pago es obligatorio")
    @DecimalMin(value = "0.01", inclusive = true, message = "El monto debe ser mayor que cero")
    private BigDecimal monto;

    @NotBlank(message = "El método de pago es obligatorio")
    @MetodoPagoValido
    private String metodoPago; // Ej: QR, TARJETA, TRANSFERENCIA
}
