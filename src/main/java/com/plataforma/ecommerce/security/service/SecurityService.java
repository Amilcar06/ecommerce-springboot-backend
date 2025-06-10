package com.plataforma.ecommerce.security.service;

import com.plataforma.ecommerce.model.Usuario;
import com.plataforma.ecommerce.model.entity.User;
import com.plataforma.ecommerce.repository.UsuarioRepository;
import com.plataforma.ecommerce.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtils jwtUtils;

    /**
     * Verifica si el usuario autenticado tiene acceso al recurso del usuario especificado
     * @param usuarioId ID del usuario al que pertenece el recurso
     * @return true si tiene acceso, false en caso contrario
     */
    public boolean tieneAccesoARecursoDeUsuario(Long usuarioId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth == null || !auth.isAuthenticated()) {
            return false;
        }
        
        // Si es admin, tiene acceso a todo
        if (tieneRol("ROLE_ADMIN")) {
            return true;
        }
        
        // Obtener el usuario autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        Long usuarioAutenticadoId = obtenerUsuarioIdPorUserId(userDetails.getId());
        
        // Verificar si el usuario autenticado es el mismo que el solicitado
        return usuarioAutenticadoId != null && usuarioAutenticadoId.equals(usuarioId);
    }
    
    /**
     * Verifica si el usuario autenticado tiene el rol especificado
     * @param rol Nombre del rol a verificar
     * @return true si tiene el rol, false en caso contrario
     */
    public boolean tieneRol(String rol) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth == null || !auth.isAuthenticated()) {
            return false;
        }
        
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> authority.equals(rol));
    }
    
    /**
     * Obtiene el ID del usuario (entidad de negocio) a partir del ID del usuario de autenticación
     * @param userId ID del usuario de autenticación
     * @return ID del usuario de negocio
     */
    public Long obtenerUsuarioIdPorUserId(Long userId) {
        return usuarioRepository.findByUserId(userId)
                .map(Usuario::getId)
                .orElse(null);
    }
    
    /**
     * Obtiene el ID del usuario autenticado actualmente
     * @return ID del usuario autenticado o null si no hay usuario autenticado
     */
    public Long obtenerUsuarioAutenticadoId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }
        
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return obtenerUsuarioIdPorUserId(userDetails.getId());
    }
}