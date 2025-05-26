package com.plataforma.ecommerce.service;


import com.plataforma.ecommerce.dto.TiendaDTO;

import java.util.List;

public interface ITiendaService {
    List<TiendaDTO> listarTiendasConProductosYResenias();
    TiendaDTO registrarTienda(TiendaDTO tiendaDTO);
    TiendaDTO actualizarTienda(Long id, TiendaDTO tiendaDTO);
    void eliminarTienda(Long id);

}