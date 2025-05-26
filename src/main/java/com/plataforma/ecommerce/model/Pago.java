package com.plataforma.ecommerce.model;

import com.plataforma.ecommerce.model.enums.EstadoPago;
import com.plataforma.ecommerce.model.enums.MetodoPago;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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

    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    // QR, TARJETA, TRANSFERENCIA
    private MetodoPago metodo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    // PENDIENTE, PAGADO, FALLIDO, REEMBOLSADO
    private EstadoPago estado;

    private LocalDateTime fecha;

    // código o detalle de transacción
    private String referencia;

    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;
}