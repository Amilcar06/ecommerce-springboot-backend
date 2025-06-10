package com.plataforma.ecommerce.repository;

import com.plataforma.ecommerce.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    /**
     * Busca un carrito por el ID del usuario
     * @param usuarioId ID del usuario
     * @return El carrito si existe
     */
    Optional<Carrito> findByUsuarioId(Long usuarioId);
}
