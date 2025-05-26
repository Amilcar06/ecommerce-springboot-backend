package com.plataforma.ecommerce.service.impl;

import com.plataforma.ecommerce.dto.ProductoDTO;
import com.plataforma.ecommerce.model.Categoria;
import com.plataforma.ecommerce.model.Producto;
import com.plataforma.ecommerce.repository.CategoriaRepository;
import com.plataforma.ecommerce.repository.ProductoRepository;
import com.plataforma.ecommerce.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements IProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    private ProductoDTO mapToDTO(Producto producto) {
        return ProductoDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .stock(producto.getStock())
                .stock_minimo(producto.getStock_minimo())
                .unidad_medida(producto.getUnidad_medida())
                .precio(producto.getPrecio())
                .categoriaId(producto.getCategoria().getId())
                .build();
    }

    private Producto mapToEntity(ProductoDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        return Producto.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .stock(dto.getStock())
                .stock_minimo(dto.getStock_minimo())
                .unidad_medida(dto.getUnidad_medida())
                .precio(dto.getPrecio())
                .categoria(categoria)
                .build();
    }

    @Override
    public List<ProductoDTO> obtenerTodos() {
        return productoRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoDTO obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    @Override
    public ProductoDTO crearProducto(ProductoDTO dto) {
        Producto producto = mapToEntity(dto);
        Producto saved = productoRepository.save(producto);
        return mapToDTO(saved);
    }

    @Override
    public ProductoDTO actualizarProducto(Long id, ProductoDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setStock(dto.getStock());
        producto.setStock_minimo(dto.getStock_minimo());
        producto.setUnidad_medida(dto.getUnidad_medida());
        producto.setPrecio(dto.getPrecio());

        // actualizar categoría si cambió
        if (!producto.getCategoria().getId().equals(dto.getCategoriaId())) {
            Categoria nuevaCategoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new IllegalArgumentException("Nueva categoría no encontrada"));
            producto.setCategoria(nuevaCategoria);
        }

        return mapToDTO(productoRepository.save(producto));
    }

    @Override
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}