package com.plataforma.ecommerce.service.impl;

import com.plataforma.ecommerce.dto.PagoRequestDTO;
import com.plataforma.ecommerce.dto.PagoResponseDTO;
import com.plataforma.ecommerce.exception.ResourceNotFoundException;
import com.plataforma.ecommerce.model.EstadoPago;
import com.plataforma.ecommerce.model.MetodoPago;
import com.plataforma.ecommerce.model.Pago;
import com.plataforma.ecommerce.model.Pedido;
import com.plataforma.ecommerce.repository.PagoRepository;
import com.plataforma.ecommerce.repository.PedidoRepository;
import com.plataforma.ecommerce.service.IPagoService;
import org.springframework.stereotype.Service;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PagoServiceImpl implements IPagoService {

    private final PagoRepository pagoRepository;
    private final PedidoRepository pedidoRepository;

    @Override
    public PagoResponseDTO registrarPago(PagoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con ID: " + dto.getPedidoId()));

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
