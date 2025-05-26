package com.plataforma.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "resenia_producto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReseniaProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer calificacion;
    private String comentario;
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}
