package com.plataforma.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "resenia_tienda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReseniaTienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer calificacion;
    private String comentario;
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "id_tienda", nullable = false)
    private Tienda tienda;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}
