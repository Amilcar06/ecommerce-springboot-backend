package com.plataforma.ecommerce.service;

import com.plataforma.ecommerce.dto.CarritoDTO;

public interface ICarritoService {
    CarritoDTO crearCarrito(CarritoDTO carritoDTO);
    CarritoDTO obtenerCarrito(Long id);
    void eliminarCarrito(Long id);
    void agregarProducto(Long carritoId, Long productoId, int cantidad);
    void eliminarProducto(Long carritoId, Long productoId);
}
