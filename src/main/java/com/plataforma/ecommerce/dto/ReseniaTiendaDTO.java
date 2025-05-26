package com.plataforma.ecommerce.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReseniaTiendaDTO {

    private Long id;

    @NotNull(message = "La calificación es obligatoria")
    @Min(value = 1, message = "La calificación mínima es 1")
    @Max(value = 5, message = "La calificación máxima es 5")
    private Integer calificacion;

    @Size(max = 500, message = "El comentario no puede exceder los 500 caracteres")
    private String comentario;

    private String usuario; // Solo para respuesta (GET)

    @NotNull(message = "El ID del usuario es obligatorio")
    @Positive(message = "El ID del usuario debe ser positivo")
    private Long usuarioId;

    @PastOrPresent(message = "La fecha no puede ser futura")
    private LocalDate fecha;

    @NotNull(message = "El ID de la tienda es obligatorio")
    @Positive(message = "El ID de la tienda debe ser positivo")
    private Long tiendaId;
}
