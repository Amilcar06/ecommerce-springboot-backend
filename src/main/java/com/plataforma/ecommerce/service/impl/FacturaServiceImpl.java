package com.plataforma.ecommerce.service.impl;

import com.plataforma.ecommerce.dto.FacturaResponseDTO;
import com.plataforma.ecommerce.model.Factura;
import com.plataforma.ecommerce.model.Pago;
import com.plataforma.ecommerce.model.Pedido;
import com.plataforma.ecommerce.model.enums.EstadoPago;
import com.plataforma.ecommerce.model.enums.EstadoPedido;
import com.plataforma.ecommerce.repository.FacturaRepository;
import com.plataforma.ecommerce.repository.PagoRepository;
import com.plataforma.ecommerce.repository.PedidoRepository;
import com.plataforma.ecommerce.service.IFacturaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FacturaServiceImpl implements IFacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private PagoRepository pagoRepository;
    
    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    @Transactional
    public FacturaResponseDTO generarFactura(Long pagoId) {
        Pago pago = pagoRepository.findById(pagoId)
                .orElseThrow(() -> new EntityNotFoundException("Pago no encontrado con ID: " + pagoId));

        // Verificar que el pago esté en estado PAGADO
        if (pago.getEstado() != EstadoPago.PAGADO) {
            throw new IllegalStateException("Solo se puede generar factura para pagos en estado PAGADO. Estado actual: " + pago.getEstado());
        }

        // Verificar que no exista ya una factura para este pago
        if (facturaRepository.existsByPagoId(pagoId)) {
            throw new IllegalStateException("Ya existe una factura para este pago.");
        }
        
        // Verificar que el pedido esté en estado PAGADO
        Pedido pedido = pago.getPedido();
        if (pedido.getEstado() != EstadoPedido.PAGADO) {
            throw new IllegalStateException("El pedido asociado al pago debe estar en estado PAGADO. Estado actual: " + pedido.getEstado());
        }
        
        // Actualizar el estado del pedido a EN_PREPARACION
        pedido.setEstado(EstadoPedido.EN_PREPARACION);
        pedidoRepository.save(pedido);

        // Generar la factura
        Factura factura = Factura.builder()
                .numero("F-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .fechaEmision(LocalDateTime.now())
                .montoTotal(pago.getMonto())
                .pago(pago)
                .build();

        facturaRepository.save(factura);

        return FacturaResponseDTO.builder()
                .id(factura.getId())
                .numero(factura.getNumero())
                .fechaEmision(factura.getFechaEmision())
                .montoTotal(factura.getMontoTotal())
                .pagoId(pagoId)
                .build();
    }

    @Override
    public FacturaResponseDTO obtenerFacturaPorId(Long id) {
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Factura no encontrada"));
        return FacturaResponseDTO.builder()
                .id(factura.getId())
                .numero(factura.getNumero())
                .fechaEmision(factura.getFechaEmision())
                .montoTotal(factura.getMontoTotal())
                .pagoId(factura.getPago().getId())
                .build();
    }
}
