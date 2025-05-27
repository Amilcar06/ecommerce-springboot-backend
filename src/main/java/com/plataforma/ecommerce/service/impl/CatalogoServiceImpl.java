package com.plataforma.ecommerce.service.impl;

import com.plataforma.ecommerce.dto.CatalogoDTO;
import com.plataforma.ecommerce.model.Catalogo;
import com.plataforma.ecommerce.repository.CatalogoRepository;
import com.plataforma.ecommerce.service.ICatalogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogoServiceImpl implements ICatalogoService {

    @Autowired
    private CatalogoRepository catalogoRepository;

    private CatalogoDTO mapToDTO(Catalogo catalogo) {
        return CatalogoDTO.builder()
                .id(catalogo.getId())
                .nombre(catalogo.getNombre())
                .descripcion(catalogo.getDescripcion())
                .fecha(catalogo.getFecha())
                .build();
    }

    private Catalogo mapToEntity(CatalogoDTO dto) {
        return Catalogo.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .fecha(dto.getFecha())
                .build();
    }

    @Override
    public List<CatalogoDTO> obtenerTodos() {
        return catalogoRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CatalogoDTO obtenerPorId(Long id) {
        return catalogoRepository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    @Override
    public CatalogoDTO crearCatalogo(CatalogoDTO dto) {
        Catalogo catalogo = mapToEntity(dto);
        Catalogo saved = catalogoRepository.save(catalogo);
        return mapToDTO(saved);
    }

    @Override
    public CatalogoDTO actualizarCatalogo(Long id, CatalogoDTO dto) {
        Catalogo catalogo = catalogoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Catálogo no encontrado"));

        catalogo.setNombre(dto.getNombre());
        catalogo.setDescripcion(dto.getDescripcion());
        catalogo.setFecha(dto.getFecha());

        return mapToDTO(catalogoRepository.save(catalogo));
    }

    @Override
    public void eliminarCatalogo(Long id) {
        catalogoRepository.deleteById(id);
    }
}