package com.plataforma.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String codigoTienda;

    private String nombre;
    private String descripcion;

    @OneToMany(mappedBy = "tienda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReseniaTienda> reseñas;

    @OneToMany(mappedBy = "tienda", cascade = CascadeType.ALL)
    private List<Categoria> categorias;


    @Transient
    private Double calificacionPromedio; // Se calcula, no se almacena en BD
}
