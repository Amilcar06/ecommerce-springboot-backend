package com.plataforma.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos de la reseña de producto")
public class ReseniaProductoDTO {

    @Schema(description = "ID único de la reseña", example = "1")
    private Long id;

    @NotNull(message = "La calificación es obligatoria")
    @Min(value = 1, message = "La calificación mínima es 1")
    @Max(value = 5, message = "La calificación máxima es 5")
    @Schema(description = "Calificación del producto (1-5 estrellas)", example = "5")
    private Integer calificacion;

    @Size(max = 500, message = "El comentario no puede exceder los 500 caracteres")
    @Schema(description = "Comentario sobre el producto", example = "Excelente producto, muy buena calidad y llegó rápido")
    private String comentario;

    @Schema(description = "Nombre del usuario que hizo la reseña", example = "Juan Pérez", accessMode = Schema.AccessMode.READ_ONLY)
    private String usuario; // Solo para lectura (GET)

    @NotNull(message = "El ID del usuario es obligatorio")
    @Positive(message = "El ID del usuario debe ser positivo")
    @Schema(description = "ID del usuario (se asigna automáticamente)", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long usuarioId;

    @PastOrPresent(message = "La fecha no puede ser futura")
    @Schema(description = "Fecha de la reseña", example = "2023-06-15", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDate fecha;

    @NotNull(message = "El ID del producto es obligatorio")
    @Positive(message = "El ID del producto debe ser positivo")
    @Schema(description = "ID del producto (se toma de la URL)", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long productoId;
}
