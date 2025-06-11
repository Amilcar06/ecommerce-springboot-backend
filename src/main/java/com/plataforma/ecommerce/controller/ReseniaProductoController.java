package com.plataforma.ecommerce.controller;

import com.plataforma.ecommerce.dto.ReseniaProductoDTO;
import com.plataforma.ecommerce.model.Usuario;
import com.plataforma.ecommerce.service.IReseniaProductoService;
import com.plataforma.ecommerce.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos/{productoId}/resenias")
@RequiredArgsConstructor
@Tag(name = "Reseñas de Productos", description = "Operaciones relacionadas con reseñas de productos")
public class ReseniaProductoController {

    private final IReseniaProductoService reseniaProductoService;
    private final IUserService userService;

    @Operation(summary = "Listar reseñas de un producto")
    @ApiResponse(responseCode = "200", description = "Reseñas obtenidas correctamente")
    @GetMapping
    public List<ReseniaProductoDTO> listarPorProducto(@PathVariable Long productoId) {
        return reseniaProductoService.obtenerReseniasPorProducto(productoId);
    }

    @Operation(summary = "Crear una reseña para un producto", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reseña creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR', 'ROLE_USUARIO')")
    public ReseniaProductoDTO crear(@PathVariable Long productoId, @Valid @RequestBody ReseniaProductoDTO dto) {
        // Obtener el usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        // Obtener el Usuario asociado al User autenticado
        Usuario usuario = userService.getUsuarioByUsername(username);
        
        // Establecer el ID del usuario y del producto en el DTO
        dto.setUsuarioId(usuario.getId());
        dto.setProductoId(productoId);
        
        return reseniaProductoService.crearResenia(dto);
    }

    @Operation(summary = "Actualizar una reseña existente", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR', 'ROLE_USUARIO')")
    public ReseniaProductoDTO actualizar(@PathVariable Long productoId, @PathVariable Long id, @Valid @RequestBody ReseniaProductoDTO dto) {
        // Obtener el usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        // Obtener el Usuario asociado al User autenticado
        Usuario usuario = userService.getUsuarioByUsername(username);
        
        // Establecer el ID del usuario y del producto en el DTO
        dto.setUsuarioId(usuario.getId());
        dto.setProductoId(productoId);
        
        return reseniaProductoService.actualizarResenia(id, dto);
    }

    @Operation(summary = "Eliminar una reseña", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR', 'ROLE_USUARIO')")
    public void eliminar(@PathVariable Long id) {
        // Obtener el usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        // Obtener el Usuario asociado al User autenticado
        Usuario usuario = userService.getUsuarioByUsername(username);
        
        // Verificar que la reseña pertenezca al usuario antes de eliminarla
        // Esta verificación debería hacerse en el servicio
        reseniaProductoService.eliminarResenia(id);
    }
}
