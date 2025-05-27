package com.plataforma.ecommerce.service;


import com.plataforma.ecommerce.dto.CategoriaDTO;
import java.util.List;

public interface ICategoriaService {
    List<CategoriaDTO> obtenerTodas();
    CategoriaDTO obtenerPorId(Long id);
    CategoriaDTO crearCategoria(CategoriaDTO categoriaDTO);
    CategoriaDTO actualizarCategoria(Long id, CategoriaDTO categoriaDTO);
    void eliminarCategoria(Long id);
}
