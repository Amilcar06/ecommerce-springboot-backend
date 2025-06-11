package com.plataforma.ecommerce.controller;

import com.plataforma.ecommerce.dto.ReseniaTiendaDTO;
import com.plataforma.ecommerce.model.Usuario;
import com.plataforma.ecommerce.service.IReseniaTiendaService;
import com.plataforma.ecommerce.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("/api/tiendas/{tiendaId}/resenias")
@RequiredArgsConstructor
@Tag(name = "Reseñas de Tiendas", description = "Operaciones relacionadas con reseñas de tiendas")
public class ReseniaTiendaController {

    private final IReseniaTiendaService reseniaTiendaService;
    private final IUserService userService;

    @Operation(summary = "Listar reseñas de una tienda")
    @ApiResponse(responseCode = "200", description = "Reseñas obtenidas correctamente")
    @GetMapping
    public List<ReseniaTiendaDTO> listarPorTienda(@PathVariable Long tiendaId) {
        return reseniaTiendaService.obtenerReseniasPorTienda(tiendaId);
    }

    @Operation(summary = "Crear una reseña para una tienda", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR', 'ROLE_USUARIO')")
    public ReseniaTiendaDTO crear(@PathVariable Long tiendaId, @Valid @RequestBody ReseniaTiendaDTO dto) {
        // Obtener el usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        // Obtener el Usuario asociado al User autenticado
        Usuario usuario = userService.getUsuarioByUsername(username);
        
        // Establecer el ID del usuario y de la tienda en el DTO
        dto.setUsuarioId(usuario.getId());
        dto.setTiendaId(tiendaId);
        
        return reseniaTiendaService.crearResenia(dto);
    }

    @Operation(summary = "Actualizar una reseña de tienda", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR', 'ROLE_USUARIO')")
    public ReseniaTiendaDTO actualizar(@PathVariable Long tiendaId, @PathVariable Long id, @Valid @RequestBody ReseniaTiendaDTO dto) {
        // Obtener el usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        // Obtener el Usuario asociado al User autenticado
        Usuario usuario = userService.getUsuarioByUsername(username);
        
        // Establecer el ID del usuario y de la tienda en el DTO
        dto.setUsuarioId(usuario.getId());
        dto.setTiendaId(tiendaId);
        
        return reseniaTiendaService.actualizarResenia(id, dto);
    }

    @Operation(summary = "Eliminar una reseña de tienda", security = @SecurityRequirement(name = "bearerAuth"))
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
        reseniaTiendaService.eliminarResenia(id);
    }
}
