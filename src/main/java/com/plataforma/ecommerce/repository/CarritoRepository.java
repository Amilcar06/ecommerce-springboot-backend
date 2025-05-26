package com.plataforma.ecommerce.repository;

import com.plataforma.ecommerce.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
}
