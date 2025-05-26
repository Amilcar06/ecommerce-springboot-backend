package com.plataforma.ecommerce.controller;

import com.plataforma.ecommerce.dto.CategoriaDTO;
import com.plataforma.ecommerce.service.ICategoriaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
@Tag(name = "Categorias", description = "Operaciones relacionadas con categorias")

public class CategoriaController {

    private final ICategoriaService categoriaService;

    @GetMapping
    public List<CategoriaDTO> obtenerCategoriasConProductos() {
        return categoriaService.obtenerCategoriasConProductos();
    }

    @GetMapping("/{tiendaId}")
    public List<CategoriaDTO> obtenerCategoriasPorTienda(@PathVariable Long tiendaId) {
        return categoriaService.obtenerCategoriasPorTienda(tiendaId);
    }

    @PostMapping
    public CategoriaDTO crearCategoria(@RequestBody CategoriaDTO dto) {
        return categoriaService.crearCategoria(dto);
    }

    @PutMapping("/{id}")
    public CategoriaDTO actualizarCategoria(@PathVariable Long id, @RequestBody CategoriaDTO dto) {
        return categoriaService.actualizarCategoria(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
    }


}
