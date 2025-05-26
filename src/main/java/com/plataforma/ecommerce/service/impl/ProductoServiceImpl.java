package com.plataforma.ecommerce.service.impl;

import com.plataforma.ecommerce.dto.ProductoDTO;
import com.plataforma.ecommerce.model.Categoria;
import com.plataforma.ecommerce.model.Producto;
import com.plataforma.ecommerce.repository.CategoriaRepository;
import com.plataforma.ecommerce.repository.ProductoRepository;
import com.plataforma.ecommerce.service.IProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements IProductoService {

    private final ProductoRepository productoRepository;

    private final CategoriaRepository categoriaRepository; // Agregar esto


    @Override
    public List<ProductoDTO> obtenerTodos() {
        return productoRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoDTO> obtenerPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoDTO> obtenerPorTienda(Long tiendaId) {
        return productoRepository.findByCategoria_Tienda_Id(tiendaId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ProductoDTO mapToDto(Producto producto) {
        return ProductoDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio()) // BigDecimal, no requiere conversión
                .stock(producto.getStock())
                .fechaRegistro(producto.getFechaRegistro())
                .estado(producto.getEstado())
                .categoriaId(producto.getCategoria().getId())
                .build();
    }

    @Override
    public ProductoDTO guardar(ProductoDTO dto) {
        // Buscar la categoría asociada
        var categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        // Construir la entidad Producto
        Producto producto = Producto.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .precio(dto.getPrecio())
                .stock(dto.getStock())
                .estado(dto.getEstado())
                .fechaRegistro(dto.getFechaRegistro() != null ? dto.getFechaRegistro() : LocalDateTime.now())
                .categoria(categoria)
                .build();

        // Guardar en la base de datos
        producto = productoRepository.save(producto);

        return mapToDto(producto);
    }

    @Override
    public ProductoDTO actualizarProducto(Long id, ProductoDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setEstado(dto.getEstado());

        // Solo actualiza la categoría si se envía otra
        if (!producto.getCategoria().getId().equals(dto.getCategoriaId())) {
            Categoria categoria = new Categoria();
            categoria.setId(dto.getCategoriaId());
            producto.setCategoria(categoria);
        }

        Producto actualizado = productoRepository.save(producto);
        return mapToDto(actualizado);
    }

    @Override
    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        productoRepository.deleteById(id);
    }




}
