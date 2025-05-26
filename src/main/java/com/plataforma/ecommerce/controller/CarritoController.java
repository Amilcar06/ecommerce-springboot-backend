package com.plataforma.ecommerce.controller;

import com.plataforma.ecommerce.dto.AgregarProductoDTO;
import com.plataforma.ecommerce.dto.CarritoDTO;
import com.plataforma.ecommerce.service.ICarritoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carritos")
@RequiredArgsConstructor
@Tag(name = "Carritos", description = "Operaciones relacionadas con carritos")
public class CarritoController {

    private final ICarritoService carritoService;

    @PostMapping
    public ResponseEntity<CarritoDTO> crearCarrito(@RequestBody CarritoDTO carritoDTO) {
        return ResponseEntity.ok(carritoService.crearCarrito(carritoDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarritoDTO> obtenerCarrito(@PathVariable Long id) {
        return ResponseEntity.ok(carritoService.obtenerCarrito(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCarrito(@PathVariable Long id) {
        carritoService.eliminarCarrito(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{carritoId}/agregar")
    public ResponseEntity<Void> agregarProducto(
            @PathVariable Long carritoId,
            @RequestBody @Valid AgregarProductoDTO dto
    ) {
        carritoService.agregarProducto(carritoId, dto.getProductoId(), dto.getCantidad());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{carritoId}/eliminar/{productoId}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long carritoId, @PathVariable Long productoId) {
        carritoService.eliminarProducto(carritoId, productoId);
        return ResponseEntity.ok().build();
    }
}

