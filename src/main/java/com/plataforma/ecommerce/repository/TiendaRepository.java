package com.plataforma.ecommerce.repository;

import com.plataforma.ecommerce.model.Tienda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TiendaRepository extends JpaRepository<Tienda, Long> {
}