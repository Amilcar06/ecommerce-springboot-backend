package com.plataforma.ecommerce.controller;

import com.plataforma.ecommerce.dto.PagoRequestDTO;
import com.plataforma.ecommerce.dto.PagoResponseDTO;
import com.plataforma.ecommerce.service.IPagoService;
import com.plataforma.ecommerce.validation.EstadoPagoValido;
import jakarta.validation.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@Tag(name = "Pagos", description = "Operaciones relacionadas con pagos")
public class PagoController {

    private final IPagoService pagoService;

    @Autowired
    public PagoController(IPagoService pagoService) {
        this.pagoService = pagoService;
    }

    @Operation(summary = "Crear un nuevo pago", description = "Registra un nuevo pago asociado a un pedido.")
    @ApiResponse(responseCode = "200", description = "Pago registrado correctamente")
    @PostMapping
    public ResponseEntity<PagoResponseDTO> crearPago(@RequestBody @Valid PagoRequestDTO dto) {
        return ResponseEntity.ok(pagoService.registrarPago(dto));
    }

    @Operation(summary = "Obtener un pago por ID")
    @GetMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> obtenerPago(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.obtenerPagoPorId(id));
    }

    @Operation(summary = "Listar pagos de un pedido específico")
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<PagoResponseDTO>> pagosPorPedido(@PathVariable Long pedidoId) {
        return ResponseEntity.ok(pagoService.obtenerPagosPorPedido(pedidoId));
    }

    @Operation(summary = "Actualizar estado de un pago")
    @PatchMapping("/{id}/estado")
    public ResponseEntity<PagoResponseDTO> actualizarEstado(
            @PathVariable Long id,
            @RequestParam @NotBlank @EstadoPagoValido(message = "El estado no puede estar vacío") String estado) {
        return ResponseEntity.ok(pagoService.actualizarEstadoPago(id, estado));
    }

    @Operation(summary = "Listar todos los pagos")
    @GetMapping
    public ResponseEntity<List<PagoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(pagoService.listarTodos());
    }
}
