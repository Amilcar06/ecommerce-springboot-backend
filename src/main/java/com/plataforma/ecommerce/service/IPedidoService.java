package com.plataforma.ecommerce.service;

import com.plataforma.ecommerce.model.Pedido;

import java.util.List;

public interface IPedidoService {
    Pedido procesarPedidoDesdeCarrito(Long carritoId, Long usuarioId);
    Pedido obtenerPedidoPorId(Long id);
    List<Pedido> listarPedidosPorUsuario(Long usuarioId);
}
