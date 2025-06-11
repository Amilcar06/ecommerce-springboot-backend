package com.plataforma.ecommerce.controller;

import com.plataforma.ecommerce.dto.CategoriaDTO;
import com.plataforma.ecommerce.service.ICategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
@Tag(name = "Categorias", description = "Operaciones relacionadas con categorias")

public class CategoriaController {

    private final ICategoriaService categoriaService;

    @Operation(summary = "Obtener todas las categorías con productos")
    @GetMapping
    public List<CategoriaDTO> obtenerCategoriasConProductos() {
        return categoriaService.obtenerCategoriasConProductos();
    }

    @Operation(summary = "Obtener categorías por tienda")
    @GetMapping("/{tiendaId}")
    public List<CategoriaDTO> obtenerCategoriasPorTienda(@PathVariable Long tiendaId) {
        return categoriaService.obtenerCategoriasPorTienda(tiendaId);
    }

    @Operation(summary = "Crear nueva categoría (solo administradores)", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CategoriaDTO crearCategoria(@RequestBody CategoriaDTO dto) {
        return categoriaService.crearCategoria(dto);
    }

    @Operation(summary = "Actualizar categoría (solo administradores)", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CategoriaDTO actualizarCategoria(@PathVariable Long id, @RequestBody CategoriaDTO dto) {
        return categoriaService.actualizarCategoria(id, dto);
    }

    @Operation(summary = "Eliminar categoría (solo administradores)", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
    }


}
