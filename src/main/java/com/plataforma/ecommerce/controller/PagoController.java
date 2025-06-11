package com.plataforma.ecommerce.controller;

import com.plataforma.ecommerce.dto.PagoRequestDTO;
import com.plataforma.ecommerce.dto.PagoResponseDTO;
import com.plataforma.ecommerce.service.IPagoService;
import com.plataforma.ecommerce.validation.EstadoPagoValido;
import jakarta.validation.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/pagos")
@Tag(name = "Pagos", description = "Operaciones relacionadas con pagos")
@SecurityRequirement(name = "bearerAuth")
public class PagoController {

    private final IPagoService pagoService;

    @Autowired
    public PagoController(IPagoService pagoService) {
        this.pagoService = pagoService;
    }

    @Operation(summary = "Crear un nuevo pago", description = "Registra un nuevo pago asociado a un pedido.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Pago registrado correctamente")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR', 'ROLE_USUARIO')")
    public ResponseEntity<PagoResponseDTO> crearPago(@RequestBody @Valid PagoRequestDTO dto) {
        return ResponseEntity.ok(pagoService.registrarPago(dto));
    }

    @Operation(summary = "Obtener un pago por ID", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR', 'ROLE_USUARIO')")
    public ResponseEntity<PagoResponseDTO> obtenerPago(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.obtenerPagoPorId(id));
    }

    @Operation(summary = "Listar pagos de un pedido específico", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/pedido/{pedidoId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR', 'ROLE_USUARIO')")
    public ResponseEntity<List<PagoResponseDTO>> pagosPorPedido(@PathVariable Long pedidoId) {
        return ResponseEntity.ok(pagoService.obtenerPagosPorPedido(pedidoId));
    }

    @Operation(summary = "Actualizar estado de un pago (solo administradores y vendedores)", security = @SecurityRequirement(name = "bearerAuth"))
    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR')")
    public ResponseEntity<PagoResponseDTO> actualizarEstado(
            @PathVariable Long id,
            @RequestParam @NotBlank @EstadoPagoValido(message = "El estado no puede estar vacío") String estado) {
        return ResponseEntity.ok(pagoService.actualizarEstadoPago(id, estado));
    }

    @Operation(summary = "Listar todos los pagos (solo administradores)", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<PagoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(pagoService.listarTodos());
    }
}
