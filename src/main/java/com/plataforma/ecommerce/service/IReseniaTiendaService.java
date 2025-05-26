package com.plataforma.ecommerce.service;

import com.plataforma.ecommerce.dto.ReseniaTiendaDTO;

import java.util.List;

public interface IReseniaTiendaService {
    List<ReseniaTiendaDTO> obtenerReseniasPorTienda(Long tiendaId);
    ReseniaTiendaDTO crearResenia(ReseniaTiendaDTO dto);
    ReseniaTiendaDTO actualizarResenia(Long id, ReseniaTiendaDTO dto);
    void eliminarResenia(Long id);

}
