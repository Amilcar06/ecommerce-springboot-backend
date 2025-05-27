package com.plataforma.ecommerce.service;

import com.plataforma.ecommerce.dto.ProductoDTO;
import java.util.List;

public interface IProductoService {
    List<ProductoDTO> obtenerTodos();
    ProductoDTO obtenerPorId(Long id);
    ProductoDTO crearProducto(ProductoDTO productoDTO);
    ProductoDTO actualizarProducto(Long id, ProductoDTO productoDTO);
    void eliminarProducto(Long id);
}