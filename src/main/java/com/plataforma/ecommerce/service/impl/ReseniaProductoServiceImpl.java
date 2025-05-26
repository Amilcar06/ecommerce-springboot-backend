package com.plataforma.ecommerce.service.impl;

import com.plataforma.ecommerce.dto.ReseniaProductoDTO;
import com.plataforma.ecommerce.model.Producto;
import com.plataforma.ecommerce.model.ReseniaProducto;
import com.plataforma.ecommerce.model.Usuario;
import com.plataforma.ecommerce.repository.ProductoRepository;
import com.plataforma.ecommerce.repository.ReseniaProductoRepository;
import com.plataforma.ecommerce.repository.UsuarioRepository;
import com.plataforma.ecommerce.service.IReseniaProductoService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReseniaProductoServiceImpl implements IReseniaProductoService {

    private final ReseniaProductoRepository reseniaProductoRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public List<ReseniaProductoDTO> obtenerReseniasPorProducto(Long productoId) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        return producto.getResenias().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReseniaProductoDTO crearResenia(ReseniaProductoDTO dto) {
        ReseniaProducto nueva = construirEntidadDesdeDTO(dto);
        return convertirADTO(reseniaProductoRepository.save(nueva));
    }

    @Override
    public ReseniaProductoDTO actualizarResenia(Long id, ReseniaProductoDTO dto) {
        ReseniaProducto existente = reseniaProductoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reseña no encontrada"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        existente.setUsuario(usuario);
        existente.setComentario(dto.getComentario());
        existente.setCalificacion(dto.getCalificacion());
        existente.setFecha(dto.getFecha());

        return convertirADTO(reseniaProductoRepository.save(existente));
    }

    @Override
    public void eliminarResenia(Long id) {
        if (!reseniaProductoRepository.existsById(id)) {
            throw new RuntimeException("Reseña no encontrada");
        }
        reseniaProductoRepository.deleteById(id);
    }

    private ReseniaProducto construirEntidadDesdeDTO(ReseniaProductoDTO dto) {
        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return ReseniaProducto.builder()
                .calificacion(dto.getCalificacion())
                .comentario(dto.getComentario())
                .usuario(usuario)
                .fecha(dto.getFecha() != null ? dto.getFecha() : LocalDate.now())
                .producto(producto)
                .build();
    }

    private ReseniaProductoDTO convertirADTO(ReseniaProducto r) {
        return ReseniaProductoDTO.builder()
                .id(r.getId())
                .calificacion(r.getCalificacion())
                .comentario(r.getComentario())
                .usuario(r.getUsuario().getNombre())
                .usuarioId(r.getUsuario().getId())
                .fecha(r.getFecha())
                .productoId(r.getProducto().getId())
                .build();
    }
}
