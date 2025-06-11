package com.plataforma.ecommerce.controller;

import com.plataforma.ecommerce.dto.TiendaDTO;
import com.plataforma.ecommerce.service.ITiendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tiendas")
@RequiredArgsConstructor
@Tag(name = "Tiendas", description = "Operaciones relacionadas con tiendas")
public class TiendaController {

    private final ITiendaService tiendaService;

    @Operation(summary = "Listar todas las tiendas con detalles")
    @GetMapping
    public List<TiendaDTO> listarTiendasConDetalles() {
        return tiendaService.listarTiendasConProductosYResenias();
    }

    @Operation(
        summary = "Registrar nueva tienda (solo administradores)", 
        security = @SecurityRequirement(name = "bearerAuth"),
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                examples = {
                    @ExampleObject(
                        name = "TechStore",
                        value = "{\n  \"codigoTienda\": \"TECH001\",\n  \"nombre\": \"TechStore Premium\",\n  \"descripcion\": \"Tienda especializada en tecnología y gaming\",\n  \"categorias\": [\"Electrónicos\", \"Gaming\", \"Computadoras\"]\n}"
                    ),
                    @ExampleObject(
                        name = "FashionStore",
                        value = "{\n  \"codigoTienda\": \"FASH001\",\n  \"nombre\": \"Fashion World\",\n  \"descripcion\": \"Ropa y accesorios de moda\",\n  \"categorias\": [\"Ropa\", \"Accesorios\", \"Calzado\"]\n}"
                    ),
                    @ExampleObject(
                        name = "HomeStore",
                        value = "{\n  \"codigoTienda\": \"HOME001\",\n  \"nombre\": \"Home & Garden\",\n  \"descripcion\": \"Todo para el hogar y jardín\",\n  \"categorias\": [\"Hogar\", \"Jardín\", \"Decoración\"]\n}"
                    )
                }
            )
        )
    )
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public TiendaDTO registrarTienda(@RequestBody TiendaDTO tiendaDTO) {
        return tiendaService.registrarTienda(tiendaDTO);
    }

    @Operation(summary = "Actualizar tienda (solo administradores)", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public TiendaDTO actualizarTienda(@PathVariable Long id, @RequestBody TiendaDTO tiendaDTO) {
        return tiendaService.actualizarTienda(id, tiendaDTO);
    }

    @Operation(summary = "Eliminar tienda (solo administradores)", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void eliminarTienda(@PathVariable Long id) {
        tiendaService.eliminarTienda(id);
    }

}
