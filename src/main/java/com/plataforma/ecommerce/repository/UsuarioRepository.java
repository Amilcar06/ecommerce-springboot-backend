package com.plataforma.ecommerce.repository;

import com.plataforma.ecommerce.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    /**
     * Busca un usuario por el username de su User asociado
     * @param username El nombre de usuario a buscar
     * @return El usuario si existe
     */
    @Query("SELECT u FROM Usuario u WHERE u.user.username = :username")
    Optional<Usuario> findByUserUsername(@Param("username") String username);
    
    /**
     * Busca un usuario por el ID de su User asociado
     * @param userId El ID del User
     * @return El usuario si existe
     */
    Optional<Usuario> findByUserId(Long userId);
}

