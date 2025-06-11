package com.plataforma.ecommerce.dto;

import com.plataforma.ecommerce.model.CarritoItem;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Item del carrito de compras")
public class CarritoItemDTO {

    @Schema(description = "ID del item", example = "1")
    private Long id;

    @NotNull(message = "El ID del producto es obligatorio")
    @Positive(message = "El ID del producto debe ser positivo")
    @Schema(description = "ID del producto", example = "1")
    private Long productoId;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Schema(description = "Nombre del producto", example = "Laptop Gaming ASUS ROG")
    private String nombreProducto;

    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    @Schema(description = "Cantidad del producto", example = "2")
    private int cantidad;

    @NotNull(message = "El precio unitario es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio unitario debe ser mayor que cero")
    @Schema(description = "Precio unitario del producto", example = "1299.99")
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
