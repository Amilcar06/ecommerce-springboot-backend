package com.plataforma.ecommerce.service.impl;

import com.plataforma.ecommerce.model.*;
import com.plataforma.ecommerce.model.enums.EstadoPedido;
import com.plataforma.ecommerce.repository.CarritoRepository;
import com.plataforma.ecommerce.repository.PedidoRepository;
import com.plataforma.ecommerce.repository.ProductoRepository;
import com.plataforma.ecommerce.repository.UsuarioRepository;
import com.plataforma.ecommerce.service.IPedidoService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements IPedidoService {

    private final PedidoRepository pedidoRepository;
    private final CarritoRepository carritoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    @Override
    @Transactional
    public Pedido procesarPedidoDesdeCarritoUsuario(Long usuarioId) {
        // Buscar usuario
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Buscar carrito del usuario
        Carrito carrito = carritoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado para el usuario"));
        
        // Verificar que el carrito tenga items
        if (carrito.getItems() == null || carrito.getItems().isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }
        
        // Verificar stock suficiente para todos los productos
        for (CarritoItem item : carrito.getItems()) {
            Producto producto = item.getProducto();
            if (producto.getStock() < item.getCantidad()) {
                throw new IllegalArgumentException(
                    "Stock insuficiente para el producto " + producto.getNombre() + 
                    ". Stock disponible: " + producto.getStock() + 
                    ", Cantidad solicitada: " + item.getCantidad());
            }
        }

        // Convertir CarritoItem → PedidoDetalle y actualizar stock
        List<PedidoDetalle> detalles = carrito.getItems().stream()
                .map(item -> {
                    // Actualizar stock del producto
                    Producto producto = item.getProducto();
                    producto.setStock(producto.getStock() - item.getCantidad());
                    productoRepository.save(producto);
                    
                    // Crear detalle del pedido
                    PedidoDetalle detalle = PedidoDetalle.builder()
                            .producto(producto)
                            .cantidad(item.getCantidad())
                            .precioUnitario(item.getPrecioUnitario())
                            .build();
                    return detalle;
                })
                .collect(Collectors.toList());

        // Crear pedido con estado PENDIENTE
        Pedido pedido = Pedido.builder()
                .fecha(LocalDateTime.now())
                .estado(EstadoPedido.PENDIENTE)
                .usuario(usuario)
                .detalles(detalles)
                .build();

        // Guardar el pedido
        Pedido pedidoGuardado = pedidoRepository.save(pedido);
        
        // Establecer la relación bidireccional
        for (PedidoDetalle detalle : pedidoGuardado.getDetalles()) {
            detalle.setPedido(pedidoGuardado);
        }
        
        pedidoGuardado = pedidoRepository.save(pedidoGuardado);

        // Limpiar el carrito después de procesar el pedido
        carrito.getItems().clear();
        carritoRepository.save(carrito);

        return pedidoGuardado;
    }

    @Override
    public Pedido obtenerPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }

    @Override
    public List<Pedido> listarPedidosPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return pedidoRepository.findByUsuario(usuario);
    }
    
    @Override
    @Transactional
    public Pedido actualizarEstadoPedido(Long id, EstadoPedido nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        
        // Validar la transición de estado
        EstadoPedido estadoActual = pedido.getEstado();
        
        // Validar transiciones de estado permitidas
        switch (estadoActual) {
            case PENDIENTE:
                // De PENDIENTE solo puede pasar a PAGADO o CANCELADO
                if (nuevoEstado != EstadoPedido.PAGADO && nuevoEstado != EstadoPedido.CANCELADO) {
                    throw new IllegalStateException(
                        "Transición de estado no permitida: " + estadoActual + " -> " + nuevoEstado);
                }
                break;
            case PAGADO:
                // De PAGADO solo puede pasar a EN_PREPARACION o CANCELADO
                if (nuevoEstado != EstadoPedido.EN_PREPARACION && nuevoEstado != EstadoPedido.CANCELADO) {
                    throw new IllegalStateException(
                        "Transición de estado no permitida: " + estadoActual + " -> " + nuevoEstado);
                }
                break;
            case EN_PREPARACION:
                // De EN_PREPARACION solo puede pasar a ENVIADO o CANCELADO
                if (nuevoEstado != EstadoPedido.ENVIADO && nuevoEstado != EstadoPedido.CANCELADO) {
                    throw new IllegalStateException(
                        "Transición de estado no permitida: " + estadoActual + " -> " + nuevoEstado);
                }
                break;
            case ENVIADO:
                // De ENVIADO solo puede pasar a ENTREGADO
                if (nuevoEstado != EstadoPedido.ENTREGADO) {
                    throw new IllegalStateException(
                        "Transición de estado no permitida: " + estadoActual + " -> " + nuevoEstado);
                }
                break;
            case ENTREGADO:
            case CANCELADO:
                // Estados finales, no pueden cambiar
                throw new IllegalStateException(
                    "No se puede cambiar el estado de un pedido en estado " + estadoActual);
        }
        
        // Actualizar el estado
        pedido.setEstado(nuevoEstado);
        return pedidoRepository.save(pedido);
    }
}
