package com.plataforma.ecommerce.service.impl;

import com.plataforma.ecommerce.dto.CarritoDTO;
import com.plataforma.ecommerce.model.*;
import com.plataforma.ecommerce.repository.*;
import com.plataforma.ecommerce.service.ICarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        carrito = carritoRepository.save(carrito);
        return CarritoDTO.fromEntity(carrito);
    }

    @Override
    public CarritoDTO obtenerCarrito(Long id) {
        Carrito carrito = carritoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        return CarritoDTO.fromEntity(carrito);
    }

    @Override
    public void eliminarCarrito(Long id) {
        Carrito carrito = carritoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        carritoItemRepository.deleteAll(carrito.getItems());
        carritoRepository.delete(carrito);
    }

    @Override
    public void agregarProducto(Long carritoId, Long productoId, int cantidad) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        CarritoItem itemExistente = carrito.getItems().stream()
                .filter(item -> item.getProducto().getId().equals(productoId))
                .findFirst()
                .orElse(null);

        if (itemExistente != null) {
            itemExistente.setCantidad(itemExistente.getCantidad() + cantidad);
            carritoItemRepository.save(itemExistente);
        } else {
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
}
