package com.plataforma.ecommerce.service.impl;

import com.plataforma.ecommerce.dto.FacturaResponseDTO;
import com.plataforma.ecommerce.model.Factura;
import com.plataforma.ecommerce.model.Pago;
import com.plataforma.ecommerce.repository.FacturaRepository;
import com.plataforma.ecommerce.repository.PagoRepository;
import com.plataforma.ecommerce.service.IFacturaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FacturaServiceImpl implements IFacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Override
    public FacturaResponseDTO generarFactura(Long pagoId) {
        Pago pago = pagoRepository.findById(pagoId)
                .orElseThrow(() -> new EntityNotFoundException("Pago no encontrado con ID: " + pagoId));

        if (!pago.getEstado().name().equals("PAGADO")) {
            throw new IllegalStateException("Solo se puede generar factura para pagos en estado PAGADO.");
        }

        if (facturaRepository.existsByPagoId(pagoId)) {
            throw new IllegalStateException("Ya existe una factura para este pago.");
        }

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
