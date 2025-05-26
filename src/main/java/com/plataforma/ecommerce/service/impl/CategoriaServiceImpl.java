package com.plataforma.ecommerce.service.impl;

import com.plataforma.ecommerce.dto.CategoriaDTO;
import com.plataforma.ecommerce.model.Categoria;
import com.plataforma.ecommerce.model.Tienda;
import com.plataforma.ecommerce.repository.CategoriaRepository;
import com.plataforma.ecommerce.service.ICategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements ICategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Override
    public List<CategoriaDTO> obtenerCategoriasConProductos() {
        return categoriaRepository.findAll().stream().map(categoria -> {
            return CategoriaDTO.builder()
                    .id(categoria.getId())
                    .nombre(categoria.getNombre())
                    .tiendaId(categoria.getTienda().getId())
                    .productoIds(
                            categoria.getProductos().stream()
                                    .map(producto -> producto.getId())
                                    .collect(Collectors.toList())
                    )
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public List<CategoriaDTO> obtenerCategoriasPorTienda(Long tiendaId) {
        return categoriaRepository.findAll().stream()
                .filter(categoria -> categoria.getTienda().getId().equals(tiendaId))
                .map(categoria -> CategoriaDTO.builder()
                        .id(categoria.getId())
                        .nombre(categoria.getNombre())
                        .tiendaId(categoria.getTienda().getId())
                        .productoIds(categoria.getProductos().stream()
                                .map(producto -> producto.getId())
                                .collect(Collectors.toList()))
                        .build()
                ).collect(Collectors.toList());
    }


    @Override
    public CategoriaDTO crearCategoria(CategoriaDTO dto) {
        var categoria = new Categoria();
        categoria.setNombre(dto.getNombre());

        var tienda = new Tienda();
        tienda.setId(dto.getTiendaId());
        categoria.setTienda(tienda);

        var guardada = categoriaRepository.save(categoria);

        return CategoriaDTO.builder()
                .id(guardada.getId())
                .nombre(guardada.getNombre())
                .tiendaId(guardada.getTienda().getId())
                .productoIds(List.of())
                .build();
    }

    @Override
    public CategoriaDTO actualizarCategoria(Long id, CategoriaDTO dto) {
        var categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        categoria.setNombre(dto.getNombre());

        if (!categoria.getTienda().getId().equals(dto.getTiendaId())) {
            var tienda = new Tienda();
            tienda.setId(dto.getTiendaId());
            categoria.setTienda(tienda);
        }

        var actualizada = categoriaRepository.save(categoria);

        return CategoriaDTO.builder()
                .id(actualizada.getId())
                .nombre(actualizada.getNombre())
                .tiendaId(actualizada.getTienda().getId())
                .productoIds(actualizada.getProductos().stream()
                        .map(p -> p.getId())
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public void eliminarCategoria(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoría no encontrada");
        }
        categoriaRepository.deleteById(id);
    }



}
