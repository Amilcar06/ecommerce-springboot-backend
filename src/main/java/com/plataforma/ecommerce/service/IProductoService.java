package com.plataforma.ecommerce.service;

import com.plataforma.ecommerce.dto.ProductoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface IProductoService {
    Page<ProductoDTO> obtenerTodos(Pageable pageable);
    List<ProductoDTO> obtenerTodos(); // Mantener compatibilidad con código existente
    Page<ProductoDTO> obtenerPorCategoria(Long categoriaId, Pageable pageable);
    List<ProductoDTO> obtenerPorCategoria(Long categoriaId); // Mantener compatibilidad
    Page<ProductoDTO> obtenerPorTienda(Long tiendaId, Pageable pageable);
    List<ProductoDTO> obtenerPorTienda(Long tiendaId); // Mantener compatibilidad
    ProductoDTO guardar(ProductoDTO productoDTO);
    ProductoDTO actualizarProducto(Long id, ProductoDTO dto);
    void eliminarProducto(Long id);
}
