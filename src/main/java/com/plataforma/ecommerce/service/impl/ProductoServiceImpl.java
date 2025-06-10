package com.plataforma.ecommerce.service.impl;

import com.plataforma.ecommerce.dto.ProductoDTO;
import com.plataforma.ecommerce.model.Categoria;
import com.plataforma.ecommerce.model.Producto;
import com.plataforma.ecommerce.repository.CategoriaRepository;
import com.plataforma.ecommerce.repository.ProductoRepository;
import com.plataforma.ecommerce.service.IProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements IProductoService {

    private final ProductoRepository productoRepository;

    private final CategoriaRepository categoriaRepository; // Agregar esto


    @Override
    public Page<ProductoDTO> obtenerTodos(Pageable pageable) {
        return productoRepository.findAll(pageable)
                .map(this::mapToDto);
    }
    
    @Override
    public List<ProductoDTO> obtenerTodos() {
        return productoRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductoDTO> obtenerPorCategoria(Long categoriaId, Pageable pageable) {
        return productoRepository.findByCategoriaId(categoriaId, pageable)
                .map(this::mapToDto);
    }
    
    @Override
    public List<ProductoDTO> obtenerPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductoDTO> obtenerPorTienda(Long tiendaId, Pageable pageable) {
        return productoRepository.findByCategoria_Tienda_Id(tiendaId, pageable)
                .map(this::mapToDto);
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
    @Transactional
    public ProductoDTO actualizarProducto(Long id, ProductoDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        // Guardar el precio original para verificar si cambió
        BigDecimal precioOriginal = producto.getPrecio();
        
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        
        // Verificar si el precio ha cambiado
        if (precioOriginal.compareTo(dto.getPrecio()) != 0) {
            // Aquí se podría implementar una lógica para verificar si hay pedidos pendientes
            // que dependan de este producto y tomar una decisión sobre si permitir el cambio
            // o crear un registro histórico de precios
            producto.setPrecio(dto.getPrecio());
        }
        
        // Actualizar stock con validación
        if (dto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        producto.setStock(dto.getStock());
        
        producto.setEstado(dto.getEstado());

        // Solo actualiza la categoría si se envía otra
        if (!producto.getCategoria().getId().equals(dto.getCategoriaId())) {
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
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
