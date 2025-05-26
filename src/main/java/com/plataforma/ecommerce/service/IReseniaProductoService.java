package com.plataforma.ecommerce.service;

import com.plataforma.ecommerce.dto.ReseniaProductoDTO;

import java.util.List;

public interface IReseniaProductoService {
    List<ReseniaProductoDTO> obtenerReseniasPorProducto(Long productoId);
    ReseniaProductoDTO crearResenia(ReseniaProductoDTO dto);
    ReseniaProductoDTO actualizarResenia(Long id, ReseniaProductoDTO dto);
    void eliminarResenia(Long id);
}
