package com.plataforma.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos de la categoría")
public class CategoriaDTO {

    @Schema(description = "ID único de la categoría", example = "1")
    private Long id;

    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    @Schema(description = "Nombre de la categoría", example = "Laptops Gaming")
    private String nombre;

    @NotNull(message = "El ID de la tienda es obligatorio")
    @Positive(message = "El ID de la tienda debe ser positivo")
    @Schema(description = "ID de la tienda a la que pertenece", example = "1")
    private Long tiendaId;

    @Size(max = 100, message = "Una categoría no puede tener más de 100 productos asignados")
    @Schema(description = "Lista de IDs de productos en esta categoría", example = "[1, 2, 3]")
    private List<@NotNull(message = "Los IDs de productos no pueden ser nulos") @Positive(message = "Los IDs de productos deben ser positivos") Long> productoIds;
}
