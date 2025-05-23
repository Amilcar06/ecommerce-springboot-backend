package com.plataforma.ecommerce.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "producto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Producto {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Date aniodeongreso;

    private String descripcion;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private int stock_minimo;

    @Column(nullable = false)
    private int unidad_medida;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Catalogo catalogo;
}

    

