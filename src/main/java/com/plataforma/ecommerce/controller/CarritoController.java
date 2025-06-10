package com.plataforma.ecommerce.controller;

import com.plataforma.ecommerce.dto.AgregarProductoDTO;
import com.plataforma.ecommerce.dto.CarritoDTO;
import com.plataforma.ecommerce.security.service.SecurityService;
import com.plataforma.ecommerce.service.ICarritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor
@Tag(name = "Carritos", description = "Operaciones relacionadas con carritos")
@SecurityRequirement(name = "bearerAuth")
public class CarritoController {

    private final ICarritoService carritoService;
    private final SecurityService securityService;

    @Operation(summary = "Obtener carrito del usuario", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{usuarioId}")
    @PreAuthorize("hasRole('ROLE_USUARIO') or hasRole('ROLE_VENDEDOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<CarritoDTO> obtenerCarritoUsuario(@PathVariable Long usuarioId) {
        // Verificar que el usuario autenticado solo pueda acceder a su propio carrito
        if (!securityService.tieneAccesoARecursoDeUsuario(usuarioId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        return ResponseEntity.ok(carritoService.obtenerCarritoPorUsuario(usuarioId));
    }

    @Operation(summary = "Agregar producto al carrito", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/{usuarioId}/agregar")
    @PreAuthorize("hasRole('ROLE_USUARIO') or hasRole('ROLE_VENDEDOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> agregarProducto(
            @PathVariable Long usuarioId,
            @RequestBody @Valid AgregarProductoDTO dto
    ) {
        // Verificar que el usuario autenticado solo pueda modificar su propio carrito
        if (!securityService.tieneAccesoARecursoDeUsuario(usuarioId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        carritoService.agregarProductoAlCarritoDeUsuario(usuarioId, dto.getProductoId(), dto.getCantidad());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Quitar producto del carrito", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/{usuarioId}/quitar/{productoId}")
    @PreAuthorize("hasRole('ROLE_USUARIO') or hasRole('ROLE_VENDEDOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> quitarProducto(
            @PathVariable Long usuarioId, 
            @PathVariable Long productoId
    ) {
        // Verificar que el usuario autenticado solo pueda modificar su propio carrito
        if (!securityService.tieneAccesoARecursoDeUsuario(usuarioId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        carritoService.eliminarProductoDelCarritoDeUsuario(usuarioId, productoId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Vaciar carrito", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/{usuarioId}/vaciar")
    @PreAuthorize("hasRole('ROLE_USUARIO') or hasRole('ROLE_VENDEDOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> vaciarCarrito(@PathVariable Long usuarioId) {
        // Verificar que el usuario autenticado solo pueda modificar su propio carrito
        if (!securityService.tieneAccesoARecursoDeUsuario(usuarioId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        carritoService.vaciarCarritoDeUsuario(usuarioId);
        return ResponseEntity.noContent().build();
    }
}

