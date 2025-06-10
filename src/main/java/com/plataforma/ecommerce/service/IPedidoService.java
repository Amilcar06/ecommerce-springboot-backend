package com.plataforma.ecommerce.service;

import com.plataforma.ecommerce.model.Pedido;
import com.plataforma.ecommerce.model.enums.EstadoPedido;

import java.util.List;

public interface IPedidoService {
    Pedido procesarPedidoDesdeCarritoUsuario(Long usuarioId);
    Pedido obtenerPedidoPorId(Long id);
    List<Pedido> listarPedidosPorUsuario(Long usuarioId);
    Pedido actualizarEstadoPedido(Long id, EstadoPedido nuevoEstado);
}
