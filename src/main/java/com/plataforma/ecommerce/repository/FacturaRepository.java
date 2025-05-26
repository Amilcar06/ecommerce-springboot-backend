package com.plataforma.ecommerce.repository;

import com.plataforma.ecommerce.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
    boolean existsByPagoId(Long pagoId);
}
