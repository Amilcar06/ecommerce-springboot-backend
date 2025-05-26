package com.plataforma.ecommerce.dto;

import com.plataforma.ecommerce.model.enums.EstadoProducto;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {

    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombre;

    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private BigDecimal precio;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    private LocalDateTime fechaRegistro;

    @NotNull(message = "El estado es obligatorio")
    private EstadoProducto estado;

    @NotNull(message = "La categoría es obligatoria")
    @Positive(message = "El ID de categoría debe ser un número positivo")
    private Long categoriaId;
}
