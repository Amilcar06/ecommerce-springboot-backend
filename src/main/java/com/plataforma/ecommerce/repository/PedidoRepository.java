package com.plataforma.ecommerce.repository;
import com.plataforma.ecommerce.model.Pedido;
import com.plataforma.ecommerce.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuario(Usuario usuario);
}
