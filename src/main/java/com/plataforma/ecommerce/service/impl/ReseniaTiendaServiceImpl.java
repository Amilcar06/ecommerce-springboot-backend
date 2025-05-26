package com.plataforma.ecommerce.service.impl;

import com.plataforma.ecommerce.dto.ReseniaTiendaDTO;
import com.plataforma.ecommerce.model.ReseniaTienda;
import com.plataforma.ecommerce.model.Tienda;
import com.plataforma.ecommerce.model.Usuario;
import com.plataforma.ecommerce.repository.ReseniaTiendaRepository;
import com.plataforma.ecommerce.repository.TiendaRepository;
import com.plataforma.ecommerce.repository.UsuarioRepository;
import com.plataforma.ecommerce.service.IReseniaTiendaService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReseniaTiendaServiceImpl implements IReseniaTiendaService {

    private final ReseniaTiendaRepository reseniaTiendaRepository;
    private final TiendaRepository tiendaRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public List<ReseniaTiendaDTO> obtenerReseniasPorTienda(Long tiendaId) {
        Tienda tienda = tiendaRepository.findById(tiendaId)
                .orElseThrow(() -> new RuntimeException("Tienda no encontrada con ID: " + tiendaId));

        return tienda.getReseñas().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReseniaTiendaDTO crearResenia(ReseniaTiendaDTO dto) {
        ReseniaTienda nueva = construirEntidadDesdeDTO(dto);
        return convertirADTO(reseniaTiendaRepository.save(nueva));
    }

    @Override
    public ReseniaTiendaDTO actualizarResenia(Long id, ReseniaTiendaDTO dto) {
        ReseniaTienda resenia = reseniaTiendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reseña no encontrada con ID: " + id));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        resenia.setUsuario(usuario);
        resenia.setComentario(dto.getComentario());
        resenia.setCalificacion(dto.getCalificacion());
        resenia.setFecha(dto.getFecha());

        return convertirADTO(reseniaTiendaRepository.save(resenia));
    }

    @Override
    public void eliminarResenia(Long id) {
        if (!reseniaTiendaRepository.existsById(id)) {
            throw new RuntimeException("Reseña no encontrada con ID: " + id);
        }
        reseniaTiendaRepository.deleteById(id);
    }

    private ReseniaTienda construirEntidadDesdeDTO(ReseniaTiendaDTO dto) {
        Tienda tienda = tiendaRepository.findById(dto.getTiendaId())
                .orElseThrow(() -> new RuntimeException("Tienda no encontrada"));
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return ReseniaTienda.builder()
                .usuario(usuario)
                .comentario(dto.getComentario())
                .calificacion(dto.getCalificacion())
                .fecha(dto.getFecha() != null ? dto.getFecha() : LocalDate.now())
                .tienda(tienda)
                .build();
    }

    private ReseniaTiendaDTO convertirADTO(ReseniaTienda r) {
        return ReseniaTiendaDTO.builder()
                .id(r.getId())
                .usuarioId(r.getUsuario().getId())
                .usuario(r.getUsuario().getNombre())
                .comentario(r.getComentario())
                .calificacion(r.getCalificacion())
                .fecha(r.getFecha())
                .tiendaId(r.getTienda().getId())
                .build();
    }
}
