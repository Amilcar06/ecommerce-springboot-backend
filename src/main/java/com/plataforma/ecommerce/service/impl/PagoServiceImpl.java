package com.plataforma.ecommerce.service.impl;

import com.plataforma.ecommerce.dto.PagoRequestDTO;
import com.plataforma.ecommerce.dto.PagoResponseDTO;
import com.plataforma.ecommerce.exception.ResourceNotFoundException;
import com.plataforma.ecommerce.model.enums.EstadoPago;
import com.plataforma.ecommerce.model.enums.EstadoPedido;
import com.plataforma.ecommerce.model.enums.MetodoPago;
import com.plataforma.ecommerce.model.Pago;
import com.plataforma.ecommerce.model.Pedido;
import com.plataforma.ecommerce.model.PedidoDetalle;
import com.plataforma.ecommerce.repository.PagoRepository;
import com.plataforma.ecommerce.repository.PedidoRepository;
import com.plataforma.ecommerce.service.IPagoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PagoServiceImpl implements IPagoService {

    private final PagoRepository pagoRepository;
    private final PedidoRepository pedidoRepository;

    @Override
    @Transactional
    public PagoResponseDTO registrarPago(PagoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con ID: " + dto.getPedidoId()));

        // Calcular el monto total del pedido
        BigDecimal montoTotal = pedido.getDetalles().stream()
                .map(detalle -> detalle.getPrecioUnitario().multiply(new BigDecimal(detalle.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // Verificar que el monto del pago coincida con el total del pedido
        if (montoTotal.compareTo(dto.getMonto()) != 0) {
            throw new IllegalArgumentException(
                "El monto del pago (" + dto.getMonto() + ") no coincide con el total del pedido (" + montoTotal + ")");
        }

        Pago pago = Pago.builder()
                .monto(dto.getMonto())
                .metodo(MetodoPago.valueOf(dto.getMetodoPago().toUpperCase()))
                .estado(EstadoPago.PENDIENTE)
                .fecha(LocalDateTime.now())
                .pedido(pedido)
                .build();

        Pago guardado = pagoRepository.save(pago);
        return mapToDTO(guardado);
    }

    @Override
    public PagoResponseDTO obtenerPagoPorId(Long id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con ID: " + id));
        return mapToDTO(pago);
    }

    @Override
    public List<PagoResponseDTO> obtenerPagosPorPedido(Long pedidoId) {
        return pagoRepository.findByPedidoId(pedidoId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PagoResponseDTO> listarTodos() {
        return pagoRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PagoResponseDTO actualizarEstadoPago(Long id, String nuevoEstado) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con ID: " + id));

        EstadoPago estado;
        try {
            estado = EstadoPago.valueOf(nuevoEstado.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado de pago inválido: " + nuevoEstado);
        }

        pago.setEstado(estado);
        pagoRepository.save(pago);
        
        // Actualizar el estado del pedido según el estado del pago
        Pedido pedido = pago.getPedido();
        if (estado == EstadoPago.PAGADO) {
            pedido.setEstado(EstadoPedido.PAGADO);
            pedidoRepository.save(pedido);
        } else if (estado == EstadoPago.FALLIDO) {
            // Si el pago falla, no cambiamos el estado del pedido, se mantiene como PENDIENTE
        } else if (estado == EstadoPago.REEMBOLSADO) {
            pedido.setEstado(EstadoPedido.CANCELADO);
            pedidoRepository.save(pedido);
        }

        return mapToDTO(pago);
    }

    private PagoResponseDTO mapToDTO(Pago pago) {
        return PagoResponseDTO.builder()
                .id(pago.getId())
                .monto(pago.getMonto())
                .metodoPago(pago.getMetodo().name())
                .estado(pago.getEstado().name())
                .fechaRegistro(LocalDateTime.parse(pago.getFecha().toString()))
                .pedidoId(pago.getPedido().getId())
                .build();
    }
}
