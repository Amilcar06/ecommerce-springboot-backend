package com.plataforma.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pago")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double monto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MetodoPago metodo; // QR, TARJETA, TRANSFERENCIA

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPago estado; // PENDIENTE, PAGADO, FALLIDO, REEMBOLSADO

    private LocalDateTime fecha;

    private String referencia; // código o detalle de transacción

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;
}