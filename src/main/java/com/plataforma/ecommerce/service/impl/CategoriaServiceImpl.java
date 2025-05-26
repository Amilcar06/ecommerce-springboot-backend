package com.plataforma.ecommerce.service.impl;


import com.plataforma.ecommerce.dto.CategoriaDTO;
import com.plataforma.ecommerce.model.Categoria;
import com.plataforma.ecommerce.repository.CategoriaRepository;
import com.plataforma.ecommerce.service.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImpl implements ICategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    private CategoriaDTO mapToDTO(Categoria categoria) {
        return CategoriaDTO.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .descripcion(categoria.getDescripcion())
                .build();
    }

    private Categoria mapToEntity(CategoriaDTO dto) {
        return Categoria.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .build();
    }

    @Override
    public List<CategoriaDTO> obtenerTodas() {
        return categoriaRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoriaDTO obtenerPorId(Long id) {
        return categoriaRepository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    @Override
    public CategoriaDTO crearCategoria(CategoriaDTO dto) {
        Categoria categoria = mapToEntity(dto);
        Categoria saved = categoriaRepository.save(categoria);
        return mapToDTO(saved);
    }

    @Override
    public CategoriaDTO actualizarCategoria(Long id, CategoriaDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());

        return mapToDTO(categoriaRepository.save(categoria));
    }

    @Override
    public void eliminarCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }
}
