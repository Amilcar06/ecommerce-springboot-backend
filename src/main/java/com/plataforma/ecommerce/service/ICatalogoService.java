package com.plataforma.ecommerce.service;

import com.plataforma.ecommerce.dto.CatalogoDTO;
import java.util.List;

public interface ICatalogoService {
    List<CatalogoDTO> obtenerTodos();
    CatalogoDTO obtenerPorId(Long id);
    CatalogoDTO crearCatalogo(CatalogoDTO catalogoDTO);
    CatalogoDTO actualizarCatalogo(Long id, CatalogoDTO catalogoDTO);
    void eliminarCatalogo(Long id);
}

