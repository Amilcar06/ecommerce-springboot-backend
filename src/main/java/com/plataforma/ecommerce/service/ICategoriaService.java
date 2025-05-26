package com.plataforma.ecommerce.service;

import com.plataforma.ecommerce.dto.CategoriaDTO;

import java.util.List;

public interface ICategoriaService {
    List<CategoriaDTO> obtenerCategoriasConProductos();
    List<CategoriaDTO> obtenerCategoriasPorTienda(Long tiendaId);
    CategoriaDTO crearCategoria(CategoriaDTO dto);
    CategoriaDTO actualizarCategoria(Long id, CategoriaDTO dto);
    void eliminarCategoria(Long id);

}
