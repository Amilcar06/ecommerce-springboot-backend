package com.plataforma.ecommerce.controller;

import com.plataforma.ecommerce.dto.TiendaDTO;
import com.plataforma.ecommerce.service.ITiendaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tiendas")
@RequiredArgsConstructor
@Tag(name = "Tiendas", description = "Operaciones relacionadas con tiendas")
public class TiendaController {

    private final ITiendaService tiendaService;

    @GetMapping
    public List<TiendaDTO> listarTiendasConDetalles() {
        return tiendaService.listarTiendasConProductosYResenias();
    }

    @PostMapping
    public TiendaDTO registrarTienda(@RequestBody TiendaDTO tiendaDTO) {
        return tiendaService.registrarTienda(tiendaDTO);
    }

    @PutMapping("/{id}")
    public TiendaDTO actualizarTienda(@PathVariable Long id, @RequestBody TiendaDTO tiendaDTO) {
        return tiendaService.actualizarTienda(id, tiendaDTO);
    }

    @DeleteMapping("/{id}")
    public void eliminarTienda(@PathVariable Long id) {
        tiendaService.eliminarTienda(id);
    }

}
