package com.plataforma.ecommerce.dto;
import lombok.*;
import jakarta.validation.constraints.*;
import com.plataforma.ecommerce.validation.MetodoPagoValido;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoRequestDTO implements Serializable {
    @NotNull(message = "El ID del pedido es obligatorio")
    @Positive(message = "El ID del pedido debe ser positivo")
    private Long pedidoId;

    @NotNull(message = "El monto del pago es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor que cero")
    private Double monto;

    @NotBlank(message = "El método de pago es obligatorio")
    @MetodoPagoValido
    private String metodoPago; // Se espera que sea: "QR", "TARJETA", "TRANSFERENCIA"
}
