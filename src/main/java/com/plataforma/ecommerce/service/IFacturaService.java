package com.plataforma.ecommerce.service;

import com.plataforma.ecommerce.dto.FacturaResponseDTO;

public interface IFacturaService {
    FacturaResponseDTO generarFactura(Long pagoId);
    FacturaResponseDTO obtenerFacturaPorId(Long id);
}
