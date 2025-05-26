package com.plataforma.ecommerce.controller;

import com.plataforma.ecommerce.dto.ReseniaTiendaDTO;
import com.plataforma.ecommerce.service.IReseniaTiendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tiendas/{tiendaId}/resenias")
@RequiredArgsConstructor
@Tag(name = "Reseñas de Tiendas", description = "Operaciones relacionadas con reseñas de tiendas")
public class ReseniaTiendaController {

    private final IReseniaTiendaService reseniaTiendaService;

    @Operation(summary = "Listar reseñas de una tienda")
    @ApiResponse(responseCode = "200", description = "Reseñas obtenidas correctamente")
    @GetMapping
    public List<ReseniaTiendaDTO> listarPorTienda(@PathVariable Long tiendaId) {
        return reseniaTiendaService.obtenerReseniasPorTienda(tiendaId);
    }

    @Operation(summary = "Crear una reseña para una tienda")
    @PostMapping
    public ReseniaTiendaDTO crear(@PathVariable Long tiendaId, @Valid @RequestBody ReseniaTiendaDTO dto) {
        dto.setTiendaId(tiendaId);
        return reseniaTiendaService.crearResenia(dto);
    }

    @Operation(summary = "Actualizar una reseña de tienda")
    @PutMapping("/{id}")
    public ReseniaTiendaDTO actualizar(@PathVariable Long id, @Valid @RequestBody ReseniaTiendaDTO dto) {
        return reseniaTiendaService.actualizarResenia(id, dto);
    }

    @Operation(summary = "Eliminar una reseña de tienda")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        reseniaTiendaService.eliminarResenia(id);
    }
}
