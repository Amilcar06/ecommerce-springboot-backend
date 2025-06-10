package com.plataforma.ecommerce.service;

import com.plataforma.ecommerce.dto.AuthDTO.ChangePasswordRequest;
import com.plataforma.ecommerce.dto.AuthDTO.UpdateProfileRequest;
import com.plataforma.ecommerce.exception.UserProfileException;
import com.plataforma.ecommerce.model.Usuario;
import com.plataforma.ecommerce.model.entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IUserService {

    User getUserByUsername(String username) throws UsernameNotFoundException;

    User updateProfile(String username, UpdateProfileRequest updateRequest) throws UserProfileException;

    void changePassword(String username, ChangePasswordRequest changePasswordRequest) throws UserProfileException;
    
    /**
     * Obtiene el Usuario asociado a un User por su username
     * @param username Nombre de usuario del User
     * @return El Usuario asociado
     */
    Usuario getUsuarioByUsername(String username);
    
    /**
     * Crea un Usuario asociado a un User existente
     * @param user El User al que se asociará el Usuario
     * @return El Usuario creado
     */
    Usuario createUsuarioForUser(User user);
    
    /**
     * Obtiene el Usuario asociado a un User por su ID
     * @param userId ID del User
     * @return El Usuario asociado
     */
    Usuario getUsuarioByUserId(Long userId);
}
