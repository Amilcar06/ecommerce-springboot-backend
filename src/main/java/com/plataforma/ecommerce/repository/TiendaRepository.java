package com.plataforma.ecommerce.repository;

import com.plataforma.ecommerce.model.Tienda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TiendaRepository extends JpaRepository<Tienda, Long> {
    Optional<Tienda> findByCodigoTienda(String codigoTienda);
}