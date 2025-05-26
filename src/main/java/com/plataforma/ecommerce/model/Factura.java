package com.plataforma.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "factura")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero; // Nro. único de factura

    private LocalDateTime fechaEmision;

    private BigDecimal montoTotal;

    @OneToOne
    @JoinColumn(name = "id_pago", nullable = false, unique = true)
    private Pago pago;
}
