package com.plataforma.ecommerce.controller;

import com.plataforma.ecommerce.dto.FacturaResponseDTO;
import com.plataforma.ecommerce.service.IFacturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/facturas")
@Tag(name = "Facturas", description = "Operaciones relacionadas con facturación")
public class FacturaController {

    @Autowired
    private IFacturaService facturaService;

    @Operation(summary = "Generar una factura para un pago")
    @PostMapping("/generar/{pagoId}")
    public ResponseEntity<FacturaResponseDTO> generarFactura(@PathVariable @Positive Long pagoId) {
        return ResponseEntity.ok(facturaService.generarFactura(pagoId));
    }

    @Operation(summary = "Obtener una factura por ID")
    @GetMapping("/{id}")
    public ResponseEntity<FacturaResponseDTO> obtenerFactura(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(facturaService.obtenerFacturaPorId(id));
    }
}
