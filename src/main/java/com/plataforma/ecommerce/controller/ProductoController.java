package com.plataforma.ecommerce.controller;

import com.plataforma.ecommerce.dto.ProductoDTO;
import com.plataforma.ecommerce.service.IProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "Operaciones relacionadas con productos")
public class ProductoController {

    private final IProductoService productoService;

    @Operation(summary = "Obtener todos los productos con paginación")
    @GetMapping
    public Page<ProductoDTO> obtenerTodosLosProductos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? 
                Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        return productoService.obtenerTodos(pageable);
    }
    
    @Operation(summary = "Obtener todos los productos sin paginación (para compatibilidad)")
    @GetMapping("/all")
    public List<ProductoDTO> obtenerTodosLosProductosSinPaginacion() {
        return productoService.obtenerTodos();
    }

    @Operation(summary = "Obtener productos por tienda con paginación")
    @GetMapping("/tienda/{tiendaId}")
    public Page<ProductoDTO> obtenerPorTienda(
            @PathVariable Long tiendaId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? 
                Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        return productoService.obtenerPorTienda(tiendaId, pageable);
    }
    
    @Operation(summary = "Obtener productos por tienda sin paginación (para compatibilidad)")
    @GetMapping("/tienda/{tiendaId}/all")
    public List<ProductoDTO> obtenerPorTiendaSinPaginacion(@PathVariable Long tiendaId) {
        return productoService.obtenerPorTienda(tiendaId);
    }

    @Operation(summary = "Obtener productos por categoría con paginación")
    @GetMapping("/categoria/{categoriaId}")
    public Page<ProductoDTO> obtenerPorCategoria(
            @PathVariable Long categoriaId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? 
                Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        return productoService.obtenerPorCategoria(categoriaId, pageable);
    }
    
    @Operation(summary = "Obtener productos por categoría sin paginación (para compatibilidad)")
    @GetMapping("/categoria/{categoriaId}/all")
    public List<ProductoDTO> obtenerPorCategoriaSinPaginacion(@PathVariable Long categoriaId) {
        return productoService.obtenerPorCategoria(categoriaId);
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> crearProducto(@Valid @RequestBody ProductoDTO dto) {
        ProductoDTO creado = productoService.guardar(dto);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ProductoDTO actualizarProducto(@PathVariable Long id, @RequestBody @Valid ProductoDTO productoDTO) {
        return productoService.actualizarProducto(id, productoDTO);
    }

    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
    }

}

