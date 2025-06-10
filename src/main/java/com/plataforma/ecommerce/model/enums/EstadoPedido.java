package com.plataforma.ecommerce.model.enums;

public enum EstadoPedido {
    PENDIENTE,      // Pedido creado pero no pagado
    PAGADO,         // Pedido pagado pero no enviado
    EN_PREPARACION, // Pedido en proceso de preparación
    ENVIADO,        // Pedido enviado
    ENTREGADO,      // Pedido entregado al cliente
    CANCELADO       // Pedido cancelado
}