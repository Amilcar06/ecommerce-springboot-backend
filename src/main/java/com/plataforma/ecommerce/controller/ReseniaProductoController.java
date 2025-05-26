package com.plataforma.ecommerce.controller;

import com.plataforma.ecommerce.dto.ReseniaProductoDTO;
import com.plataforma.ecommerce.service.IReseniaProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos/{productoId}/resenias")
@RequiredArgsConstructor
@Tag(name = "Reseñas de Productos", description = "Operaciones relacionadas con reseñas de productos")
public class ReseniaProductoController {

    private final IReseniaProductoService reseniaProductoService;

    @Operation(summary = "Listar reseñas de un producto")
    @ApiResponse(responseCode = "200", description = "Reseñas obtenidas correctamente")
    @GetMapping
    public List<ReseniaProductoDTO> listarPorProducto(@PathVariable Long productoId) {
        return reseniaProductoService.obtenerReseniasPorProducto(productoId);
    }

    @Operation(summary = "Crear una reseña para un producto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reseña creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ReseniaProductoDTO crear(@PathVariable Long productoId, @Valid @RequestBody ReseniaProductoDTO dto) {
        dto.setProductoId(productoId);
        return reseniaProductoService.crearResenia(dto);
    }

    @Operation(summary = "Actualizar una reseña existente")
    @PutMapping("/{id}")
    public ReseniaProductoDTO actualizar(@PathVariable Long id, @Valid @RequestBody ReseniaProductoDTO dto) {
        return reseniaProductoService.actualizarResenia(id, dto);
    }

    @Operation(summary = "Eliminar una reseña")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        reseniaProductoService.eliminarResenia(id);
    }
}
