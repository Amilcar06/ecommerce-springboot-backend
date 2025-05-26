package com.plataforma.ecommerce.repository;
import com.plataforma.ecommerce.model.Pago;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByPedidoId(Long pedidoId);
}
