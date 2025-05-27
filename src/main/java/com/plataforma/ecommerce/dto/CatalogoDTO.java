package com.plataforma.ecommerce.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

public class CatalogoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDate fecha;
}
