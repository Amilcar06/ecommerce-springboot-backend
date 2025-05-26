package com.plataforma.ecommerce.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {

    private Long id;

    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombre;

    @NotNull(message = "El ID de la tienda es obligatorio")
    @Positive(message = "El ID de la tienda debe ser positivo")
    private Long tiendaId;

    @Size(max = 100, message = "Una categoría no puede tener más de 100 productos asignados")
    private List<@NotNull(message = "Los IDs de productos no pueden ser nulos") @Positive(message = "Los IDs de productos deben ser positivos") Long> productoIds;
}
