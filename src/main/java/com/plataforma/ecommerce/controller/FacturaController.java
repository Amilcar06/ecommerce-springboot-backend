package com.plataforma.ecommerce.controller;

import com.plataforma.ecommerce.dto.FacturaResponseDTO;
import com.plataforma.ecommerce.service.IFacturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/facturas")
@Tag(name = "Facturas", description = "Operaciones relacionadas con facturación")
@SecurityRequirement(name = "bearerAuth")
public class FacturaController {

    @Autowired
    private IFacturaService facturaService;

    @Operation(summary = "Generar una factura para un pago", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/generar/{pagoId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR', 'ROLE_USUARIO')")
    public ResponseEntity<FacturaResponseDTO> generarFactura(@PathVariable @Positive Long pagoId) {
        return ResponseEntity.ok(facturaService.generarFactura(pagoId));
    }

    @Operation(summary = "Obtener una factura por ID", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR', 'ROLE_USUARIO')")
    public ResponseEntity<FacturaResponseDTO> obtenerFactura(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(facturaService.obtenerFacturaPorId(id));
    }
}
