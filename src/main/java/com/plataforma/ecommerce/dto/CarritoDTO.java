package com.plataforma.ecommerce.dto;

import com.plataforma.ecommerce.model.Carrito;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarritoDTO {

    private Long id;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Positive(message = "El ID del usuario debe ser positivo")
    private Long usuarioId;

    @NotEmpty(message = "El carrito debe contener al menos un producto")
    private List<@Valid CarritoItemDTO> items;

    public static CarritoDTO fromEntity(Carrito carrito) {
        return new CarritoDTO(
                carrito.getId(),
                carrito.getUsuario().getId(),
                carrito.getItems().stream()
                        .map(CarritoItemDTO::fromEntity)
                        .collect(Collectors.toList())
        );
    }
}
