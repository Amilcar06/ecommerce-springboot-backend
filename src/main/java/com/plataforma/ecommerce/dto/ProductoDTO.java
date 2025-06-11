package com.plataforma.ecommerce.dto;

import com.plataforma.ecommerce.model.enums.EstadoProducto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos del producto")
public class ProductoDTO {

    @Schema(description = "ID único del producto", example = "1")
    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    @Schema(description = "Nombre del producto", example = "Laptop Gaming ASUS ROG")
    private String nombre;

    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
    @Schema(description = "Descripción del producto", example = "Laptop gaming con procesador Intel i7, 16GB RAM, RTX 3060")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    @Schema(description = "Precio del producto", example = "1299.99")
    private BigDecimal precio;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Schema(description = "Cantidad en stock", example = "25")
    private Integer stock;

    @Schema(description = "Fecha de registro del producto", example = "2023-06-15T10:30:00")
    private LocalDateTime fechaRegistro;

    @NotNull(message = "El estado es obligatorio")
    @Schema(description = "Estado del producto", example = "ACTIVO", allowableValues = {"ACTIVO", "INACTIVO", "AGOTADO"})
    private EstadoProducto estado;

    @NotNull(message = "La categoría es obligatoria")
    @Positive(message = "El ID de categoría debe ser un número positivo")
    @Schema(description = "ID de la categoría", example = "1")
    private Long categoriaId;
}
