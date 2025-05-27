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

public class ProductoDTO {
    private Long id;
    private String nombre;
    private LocalDate fecha_ingreso;
    private String descripcion;
    private int stock;
    private int stock_minimo;
    private String unidad_medida;
    private double precio;
    private Long categoriaId;
}
