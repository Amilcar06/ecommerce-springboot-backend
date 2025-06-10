package com.plataforma.ecommerce.service.impl;

import com.plataforma.ecommerce.dto.CarritoDTO;
import com.plataforma.ecommerce.model.*;
import com.plataforma.ecommerce.repository.*;
import com.plataforma.ecommerce.service.ICarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CarritoServiceImpl implements ICarritoService {

    private final CarritoRepository carritoRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CarritoItemRepository carritoItemRepository;
    
    @Override
    public CarritoDTO crearCarrito(CarritoDTO carritoDTO) {
        Usuario usuario = usuarioRepository.findById(carritoDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        Carrito carrito = Carrito.builder()
                .usuario(usuario)
                .build();
        
        return CarritoDTO.fromEntity(carritoRepository.save(carrito));
    }
    
    @Override
    public CarritoDTO obtenerCarrito(Long id) {
        Carrito carrito = carritoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        
        return CarritoDTO.fromEntity(carrito);
    }
    
    @Override
    public void eliminarCarrito(Long id) {
        if (!carritoRepository.existsById(id)) {
            throw new RuntimeException("Carrito no encontrado");
        }
        carritoRepository.deleteById(id);
    }
    
    @Override
    public void agregarProducto(Long carritoId, Long productoId, int cantidad) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        // Verificar stock disponible
        if (producto.getStock() < cantidad) {
            throw new IllegalArgumentException("Stock insuficiente. Stock disponible: " + producto.getStock());
        }
        
        // Verificar si el producto ya está en el carrito
        CarritoItem itemExistente = carrito.getItems().stream()
                .filter(item -> item.getProducto().getId().equals(productoId))
                .findFirst()
                .orElse(null);
        
        if (itemExistente != null) {
            // Verificar stock para la cantidad total
            int cantidadTotal = itemExistente.getCantidad() + cantidad;
            if (producto.getStock() < cantidadTotal) {
                throw new IllegalArgumentException("Stock insuficiente para la cantidad total. Stock disponible: " + producto.getStock());
            }
            
            // Actualizar cantidad si ya existe
            itemExistente.setCantidad(cantidadTotal);
            carritoItemRepository.save(itemExistente);
        } else {
            // Agregar nuevo item si no existe
            CarritoItem nuevoItem = CarritoItem.builder()
                    .carrito(carrito)
                    .producto(producto)
                    .cantidad(cantidad)
                    .precioUnitario(producto.getPrecio())
                    .build();
            carritoItemRepository.save(nuevoItem);
        }
    }
    
    @Override
    public void eliminarProducto(Long carritoId, Long productoId) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        
        carrito.getItems().stream()
                .filter(item -> item.getProducto().getId().equals(productoId))
                .findFirst()
                .ifPresent(item -> carritoItemRepository.delete(item));
    }

    @Override
    @Transactional
    public CarritoDTO obtenerCarritoPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Buscar el carrito del usuario o crear uno nuevo si no existe
        Carrito carrito = carritoRepository.findByUsuarioId(usuarioId)
                .orElseGet(() -> {
                    Carrito nuevoCarrito = Carrito.builder()
                            .usuario(usuario)
                            .build();
                    return carritoRepository.save(nuevoCarrito);
                });
        
        return CarritoDTO.fromEntity(carrito);
    }

    @Override
    @Transactional
    public void agregarProductoAlCarritoDeUsuario(Long usuarioId, Long productoId, int cantidad) {
        // Validar cantidad
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
        
        // Obtener el carrito del usuario (o crear uno nuevo si no existe)
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        Carrito carrito = carritoRepository.findByUsuarioId(usuarioId)
                .orElseGet(() -> {
                    Carrito nuevoCarrito = Carrito.builder()
                            .usuario(usuario)
                            .build();
                    return carritoRepository.save(nuevoCarrito);
                });

        // Obtener el producto
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
                
        // Verificar stock disponible
        if (producto.getStock() < cantidad) {
            throw new IllegalArgumentException("Stock insuficiente. Stock disponible: " + producto.getStock());
        }

        // Verificar si el producto ya está en el carrito
        CarritoItem itemExistente = carrito.getItems().stream()
                .filter(item -> item.getProducto().getId().equals(productoId))
                .findFirst()
                .orElse(null);

        if (itemExistente != null) {
            // Verificar stock para la cantidad total
            int cantidadTotal = itemExistente.getCantidad() + cantidad;
            if (producto.getStock() < cantidadTotal) {
                throw new IllegalArgumentException("Stock insuficiente para la cantidad total. Stock disponible: " + producto.getStock());
            }
            
            // Actualizar cantidad si ya existe
            itemExistente.setCantidad(cantidadTotal);
            carritoItemRepository.save(itemExistente);
        } else {
            // Agregar nuevo item si no existe
            CarritoItem nuevoItem = CarritoItem.builder()
                    .carrito(carrito)
                    .producto(producto)
                    .cantidad(cantidad)
                    .precioUnitario(producto.getPrecio())
                    .build();
            carritoItemRepository.save(nuevoItem);
        }
    }

    @Override
    @Transactional
    public void eliminarProductoDelCarritoDeUsuario(Long usuarioId, Long productoId) {
        Carrito carrito = carritoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado para el usuario"));

        carrito.getItems().stream()
                .filter(item -> item.getProducto().getId().equals(productoId))
                .findFirst()
                .ifPresent(item -> carritoItemRepository.delete(item));
    }

    @Override
    @Transactional
    public void vaciarCarritoDeUsuario(Long usuarioId) {
        Carrito carrito = carritoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado para el usuario"));
        
        carritoItemRepository.deleteAll(carrito.getItems());
        carrito.getItems().clear();
        carritoRepository.save(carrito);
    }
}
