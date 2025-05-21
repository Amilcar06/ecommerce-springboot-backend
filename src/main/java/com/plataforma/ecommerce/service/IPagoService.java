package com.plataforma.ecommerce.service;

import com.plataforma.ecommerce.dto.PagoRequestDTO;
import com.plataforma.ecommerce.dto.PagoResponseDTO;

import java.util.List;

public interface IPagoService {
    PagoResponseDTO registrarPago(PagoRequestDTO dto);
    PagoResponseDTO obtenerPagoPorId(Long id);
    List<PagoResponseDTO> obtenerPagosPorPedido(Long pedidoId);
    List<PagoResponseDTO> listarTodos();
    PagoResponseDTO actualizarEstadoPago(Long id, String nuevoEstado);
}
