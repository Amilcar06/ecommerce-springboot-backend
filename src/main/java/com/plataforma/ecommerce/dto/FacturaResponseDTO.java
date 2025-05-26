package com.plataforma.ecommerce.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacturaResponseDTO {

    private Long id;

    @NotBlank(message = "El número de factura es obligatorio")
    private String numero;

    @NotNull(message = "La fecha de emisión es obligatoria")
    private LocalDateTime fechaEmision;

    @NotNull(message = "El monto total es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto total debe ser mayor a cero")
    private BigDecimal montoTotal;

    @NotNull(message = "El ID del pago es obligatorio")
    @Positive(message = "El ID del pago debe ser positivo")
    private Long pagoId;
}
