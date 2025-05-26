package com.plataforma.ecommerce.service.impl;

import com.plataforma.ecommerce.dto.TiendaDTO;
import com.plataforma.ecommerce.model.Tienda;
import com.plataforma.ecommerce.repository.TiendaRepository;
import com.plataforma.ecommerce.service.ITiendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TiendaServiceImpl implements ITiendaService {

    private final TiendaRepository tiendaRepository;


    @Override
    public List<TiendaDTO> listarTiendasConProductosYResenias() {
        return tiendaRepository.findAll().stream().map(tienda -> {
            TiendaDTO dto = TiendaDTO.builder()
                    .id(tienda.getId())
                    .codigoTienda(tienda.getCodigoTienda())
                    .nombre(tienda.getNombre())
                    .descripcion(tienda.getDescripcion())
                    .calificacionPromedio(tienda.getCalificacionPromedio())
                    .build();

            // Mapeamos nombres de las categorías
            var categorias = tienda.getCategorias().stream()
                    .map(categoria -> categoria.getNombre())
                    .collect(Collectors.toList());

            dto.setCategorias(categorias);

            return dto;
        }).collect(Collectors.toList());
    }


    @Override
    public TiendaDTO registrarTienda(TiendaDTO tiendaDTO) {
        Tienda tienda = new Tienda();
        tienda.setCodigoTienda(tiendaDTO.getCodigoTienda());
        tienda.setNombre(tiendaDTO.getNombre());
        tienda.setDescripcion(tiendaDTO.getDescripcion());
        tiendaRepository.save(tienda);

        tiendaDTO.setId(tienda.getId());
        return tiendaDTO;
    }

    @Override
    public TiendaDTO actualizarTienda(Long id, TiendaDTO tiendaDTO) {
        Tienda tienda = tiendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tienda no encontrada con ID: " + id));

        tienda.setCodigoTienda(tiendaDTO.getCodigoTienda());
        tienda.setNombre(tiendaDTO.getNombre());
        tienda.setDescripcion(tiendaDTO.getDescripcion());
        tiendaRepository.save(tienda);

        tiendaDTO.setId(tienda.getId());
        return tiendaDTO;
    }

    @Override
    public void eliminarTienda(Long id) {
        if (!tiendaRepository.existsById(id)) {
            throw new RuntimeException("Tienda no encontrada con ID: " + id);
        }
        tiendaRepository.deleteById(id);
    }






}
