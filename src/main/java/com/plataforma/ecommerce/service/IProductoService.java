package com.plataforma.ecommerce.service;

import com.plataforma.ecommerce.dto.ProductoDTO;

import java.util.List;
public interface IProductoService {
    List<ProductoDTO> obtenerTodos();
    List<ProductoDTO> obtenerPorCategoria(Long categoriaId);
    List<ProductoDTO> obtenerPorTienda(Long tiendaId);
    ProductoDTO guardar(ProductoDTO productoDTO);
    ProductoDTO actualizarProducto(Long id, ProductoDTO dto);
    void eliminarProducto(Long id);

}
