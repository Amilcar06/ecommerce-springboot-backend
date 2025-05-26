package com.plataforma.ecommerce.repository;

import com.plataforma.ecommerce.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}

