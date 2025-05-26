package com.plataforma.ecommerce.repository;

import com.plataforma.ecommerce.model.PedidoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoDetalleRepository extends JpaRepository<PedidoDetalle, Long> {
}
