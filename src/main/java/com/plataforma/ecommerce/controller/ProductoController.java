package com.plataforma.ecommerce.controller;

import com.plataforma.ecommerce.dto.ProductoDTO;
import com.plataforma.ecommerce.service.IProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Operation(
        summary = "Crear nuevo producto (solo vendedores y administradores)", 
        security = @SecurityRequirement(name = "bearerAuth"),
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                examples = {
                    @ExampleObject(
                        name = "Laptop Gaming",
                        value = "{\n  \"nombre\": \"Laptop Gaming ASUS ROG\",\n  \"descripcion\": \"Laptop gaming con procesador Intel i7, 16GB RAM, RTX 3060\",\n  \"precio\": 1299.99,\n  \"stock\": 25,\n  \"estado\": \"ACTIVO\",\n  \"categoriaId\": 1\n}"
                    ),
                    @ExampleObject(
                        name = "Mouse Gaming",
                        value = "{\n  \"nombre\": \"Mouse Gaming Logitech G502\",\n  \"descripcion\": \"Mouse gaming con sensor óptico de alta precisión\",\n  \"precio\": 79.99,\n  \"stock\": 50,\n  \"estado\": \"ACTIVO\",\n  \"categoriaId\": 2\n}"
                    ),
                    @ExampleObject(
                        name = "Teclado Mecánico",
                        value = "{\n  \"nombre\": \"Teclado Mecánico Corsair K95\",\n  \"descripcion\": \"Teclado mecánico RGB con switches Cherry MX\",\n  \"precio\": 199.99,\n  \"stock\": 30,\n  \"estado\": \"ACTIVO\",\n  \"categoriaId\": 2\n}"
                    )
                }
            )
        )
    )
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_VENDEDOR')")
    public ResponseEntity<ProductoDTO> crearProducto(@Valid @RequestBody ProductoDTO dto) {
        ProductoDTO creado = productoService.guardar(dto);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar producto (solo vendedores y administradores)", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_VENDEDOR')")
    public ProductoDTO actualizarProducto(@PathVariable Long id, @RequestBody @Valid ProductoDTO productoDTO) {
        return productoService.actualizarProducto(id, productoDTO);
    }

    @Operation(summary = "Eliminar producto (solo vendedores y administradores)", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_VENDEDOR')")
    public void eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
    }

}

