package com.plataforma.ecommerce.dto;

import com.plataforma.ecommerce.model.CarritoItem;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarritoItemDTO {

    private Long id;

    @NotNull(message = "El ID del producto es obligatorio")
    @Positive(message = "El ID del producto debe ser positivo")
    private Long productoId;

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombreProducto;

    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private int cantidad;

    @NotNull(message = "El precio unitario es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio unitario debe ser mayor que cero")
    private BigDecimal precioUnitario;

    public static CarritoItemDTO fromEntity(CarritoItem item) {
        return new CarritoItemDTO(
                item.getId(),
                item.getProducto().getId(),
                item.getProducto().getNombre(),
                item.getCantidad(),
                item.getPrecioUnitario()
        );
    }
}
