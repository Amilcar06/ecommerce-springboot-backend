package com.plataforma.ecommerce.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
 

public class CatalogoDTO {
    private Long id;
    private String nombre;
    private String description;
    private LocalDate fecha;
}
