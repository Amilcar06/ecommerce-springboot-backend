package com.plataforma.ecommerce.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TiendaDTO {

    private Long id;

    @NotBlank(message = "El código de la tienda es obligatorio")
    @Size(max = 50, message = "El código de la tienda no puede exceder los 50 caracteres")
    private String codigoTienda;

    @NotBlank(message = "El nombre de la tienda es obligatorio")
    @Size(max = 100, message = "El nombre de la tienda no puede exceder los 100 caracteres")
    private String nombre;

    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
    private String descripcion;

    @DecimalMin(value = "0.0", inclusive = true, message = "La calificación promedio no puede ser negativa")
    @DecimalMax(value = "5.0", inclusive = true, message = "La calificación promedio no puede ser mayor a 5")
    private Double calificacionPromedio;

    @Size(max = 50, message = "Solo se permiten hasta 50 categorías")
    private List<@NotBlank(message = "Las categorías no pueden estar vacías") String> categorias;
}
