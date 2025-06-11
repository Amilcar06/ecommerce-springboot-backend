package com.plataforma.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos de la tienda")
public class TiendaDTO {

    @Schema(description = "ID único de la tienda", example = "1")
    private Long id;

    @NotBlank(message = "El código de la tienda es obligatorio")
    @Size(max = 50, message = "El código de la tienda no puede exceder los 50 caracteres")
    @Schema(description = "Código único de la tienda", example = "TECH001")
    private String codigoTienda;

    @NotBlank(message = "El nombre de la tienda es obligatorio")
    @Size(max = 100, message = "El nombre de la tienda no puede exceder los 100 caracteres")
    @Schema(description = "Nombre de la tienda", example = "TechStore Premium")
    private String nombre;

    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
    @Schema(description = "Descripción de la tienda", example = "Tienda especializada en tecnología y gaming")
    private String descripcion;

    @DecimalMin(value = "0.0", inclusive = true, message = "La calificación promedio no puede ser negativa")
    @DecimalMax(value = "5.0", inclusive = true, message = "La calificación promedio no puede ser mayor a 5")
    @Schema(description = "Calificación promedio de la tienda", example = "4.5")
    private Double calificacionPromedio;

    @Size(max = 50, message = "Solo se permiten hasta 50 categorías")
    @Schema(description = "Lista de categorías de la tienda", example = "[\"Electrónicos\", \"Gaming\", \"Computadoras\"]")
    private List<@NotBlank(message = "Las categorías no pueden estar vacías") String> categorias;
}
