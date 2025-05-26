package com.plataforma.ecommerce.controller;

import com.plataforma.ecommerce.dto.ProductoDTO;
import com.plataforma.ecommerce.service.IProductoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    // GET /api/productos
    @GetMapping
    public List<ProductoDTO> obtenerTodosLosProductos() {
        return productoService.obtenerTodos();
    }


    // GET /api/tiendas/{id}/productos
    @GetMapping("/tienda/{tiendaId}")
    public List<ProductoDTO> obtenerPorTienda(@PathVariable Long tiendaId) {
        return productoService.obtenerPorTienda(tiendaId);
    }

    // GET /api/categorias/{id}/productos
    @GetMapping("/categoria/{categoriaId}")
    public List<ProductoDTO> obtenerPorCategoria(@PathVariable Long categoriaId) {
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

