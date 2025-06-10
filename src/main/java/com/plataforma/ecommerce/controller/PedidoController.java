package com.plataforma.ecommerce.controller;

import com.plataforma.ecommerce.dto.PedidoDTO;
import com.plataforma.ecommerce.model.Pedido;
import com.plataforma.ecommerce.model.enums.EstadoPedido;
import com.plataforma.ecommerce.security.service.SecurityService;
import com.plataforma.ecommerce.service.IPedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Operaciones relacionadas con pedidos")
@SecurityRequirement(name = "bearerAuth")
public class PedidoController {

    private final IPedidoService pedidoService;
    private final SecurityService securityService;

    @Operation(summary = "Procesar pedido desde carrito del usuario", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/{usuarioId}")
    @PreAuthorize("hasRole('ROLE_USUARIO') or hasRole('ROLE_VENDEDOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<PedidoDTO> procesarPedido(@PathVariable Long usuarioId) {
        // Verificar que el usuario autenticado solo pueda procesar su propio pedido
        if (!securityService.tieneAccesoARecursoDeUsuario(usuarioId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        Pedido pedido = pedidoService.procesarPedidoDesdeCarritoUsuario(usuarioId);
        return ResponseEntity.ok(PedidoDTO.fromEntity(pedido));
    }

    @Operation(summary = "Obtener pedido por ID", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USUARIO') or hasRole('ROLE_VENDEDOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<PedidoDTO> obtenerPedidoPorId(@PathVariable Long id) {
        // Obtener el pedido
        Pedido pedido = pedidoService.obtenerPedidoPorId(id);
        
        // Verificar que el usuario autenticado solo pueda ver sus propios pedidos
        if (!securityService.tieneAccesoARecursoDeUsuario(pedido.getUsuario().getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        return ResponseEntity.ok(PedidoDTO.fromEntity(pedido));
    }

    @Operation(summary = "Listar pedidos por usuario", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasRole('ROLE_USUARIO') or hasRole('ROLE_VENDEDOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<PedidoDTO>> listarPedidosPorUsuario(@PathVariable Long usuarioId) {
        // Verificar que el usuario autenticado solo pueda ver sus propios pedidos
        if (!securityService.tieneAccesoARecursoDeUsuario(usuarioId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        List<Pedido> pedidos = pedidoService.listarPedidosPorUsuario(usuarioId);
        List<PedidoDTO> pedidosDTO = pedidos.stream()
                .map(PedidoDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pedidosDTO);
    }
    
    @Operation(summary = "Actualizar estado de un pedido", security = @SecurityRequirement(name = "bearerAuth"))
    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasRole('ROLE_VENDEDOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<PedidoDTO> actualizarEstadoPedido(
            @PathVariable Long id,
            @RequestParam EstadoPedido estado) {
        
        // Solo vendedores y administradores pueden actualizar estados
        if (!securityService.tieneRol("ROLE_VENDEDOR") && !securityService.tieneRol("ROLE_ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        try {
            Pedido pedido = pedidoService.actualizarEstadoPedido(id, estado);
            return ResponseEntity.ok(PedidoDTO.fromEntity(pedido));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}

