package com.plataforma.ecommerce.controller;

import com.plataforma.ecommerce.dto.PedidoDTO;
import com.plataforma.ecommerce.model.Pedido;
import com.plataforma.ecommerce.service.IPedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Operaciones relacionadas con pedidos")
public class PedidoController {

    private final IPedidoService pedidoService;

    @Operation(summary = "Procesar pedido desde carrito")
    // Nuevo endpoint actualizado que incluye usuarioId
    @PostMapping("/procesar/{carritoId}/usuario/{usuarioId}")
    public ResponseEntity<Pedido> procesarPedido(
            @PathVariable Long carritoId,
            @PathVariable Long usuarioId
    ) {
        Pedido pedido = pedidoService.procesarPedidoDesdeCarrito(carritoId, usuarioId);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> obtenerPedidoPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.obtenerPedidoPorId(id);
        return ResponseEntity.ok(PedidoDTO.fromEntity(pedido));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PedidoDTO>> listarPedidosPorUsuario(@PathVariable Long usuarioId) {
        List<Pedido> pedidos = pedidoService.listarPedidosPorUsuario(usuarioId);
        List<PedidoDTO> pedidosDTO = pedidos.stream()
                .map(PedidoDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pedidosDTO);
    }

}

