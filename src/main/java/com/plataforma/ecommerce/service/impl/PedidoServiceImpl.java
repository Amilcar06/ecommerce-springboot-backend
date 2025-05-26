package com.plataforma.ecommerce.service.impl;

import com.plataforma.ecommerce.model.*;
import com.plataforma.ecommerce.repository.CarritoRepository;
import com.plataforma.ecommerce.repository.PedidoRepository;
import com.plataforma.ecommerce.repository.UsuarioRepository;
import com.plataforma.ecommerce.service.IPedidoService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements IPedidoService {

    private final PedidoRepository pedidoRepository;
    private final CarritoRepository carritoRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public Pedido procesarPedidoDesdeCarrito(Long carritoId, Long usuarioId) {
        // Buscar carrito
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        // Validar que el carrito no esté vacío
        if (carrito.getItems() == null || carrito.getItems().isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }

        // Buscar usuario
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Convertir CarritoItem → PedidoDetalle
        List<PedidoDetalle> detalles = carrito.getItems().stream()
                .map(item -> PedidoDetalle.builder()
                        .producto(item.getProducto())
                        .cantidad(item.getCantidad())
                        .precioUnitario(item.getPrecioUnitario())
                        .build())
                .collect(Collectors.toList());

        // Crear pedido
        Pedido pedido = Pedido.builder()
                .fecha(LocalDateTime.now())
                .usuario(usuario)
                .detalles(detalles)
                .build();

        // Guardar el pedido
        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        // Limpiar el carrito (opcional según la lógica de negocio)
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
}
