package com.plataforma.ecommerce.dto;

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

public class CategoriaDTO {
    private Long id;
    private String nombre;
    private String descripcion;    
    private Long idCatalago;
}
