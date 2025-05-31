package com.plataforma.ecommerce.service;

import com.plataforma.ecommerce.dto.AuthDTO.ChangePasswordRequest;
import com.plataforma.ecommerce.dto.AuthDTO.UpdateProfileRequest;
import com.plataforma.ecommerce.exception.UserProfileException;
import com.plataforma.ecommerce.model.entity.User;
import com.plataforma.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    /**
     * Obtiene un usuario por su nombre de usuario
     * @param username Nombre de usuario
     * @return Usuario encontrado
     * @throws UsernameNotFoundException Si el usuario no existe
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }
    
    /**
     * Actualiza el perfil de un usuario
     * @param username Nombre de usuario actual
     * @param updateRequest Datos a actualizar
     * @return Usuario actualizado
     * @throws UserProfileException Si hay problemas al actualizar el perfil
     */
    @Transactional
    public User updateProfile(String username, UpdateProfileRequest updateRequest) {
        User user = getUserByUsername(username);
        
        // Actualizar solo los campos proporcionados
        if (updateRequest.getUsername() != null && !updateRequest.getUsername().isEmpty()) {
            // Verificar que el nuevo username no exista ya (si es diferente al actual)
            if (!username.equals(updateRequest.getUsername()) && 
                userRepository.existsByUsername(updateRequest.getUsername())) {
                throw new UserProfileException("El nombre de usuario ya está en uso");
            }
            user.setUsername(updateRequest.getUsername());
        }
        
        if (updateRequest.getEmail() != null && !updateRequest.getEmail().isEmpty()) {
            // Verificar que el nuevo email no exista ya (si es diferente al actual)
            if (!user.getEmail().equals(updateRequest.getEmail()) &&
                userRepository.existsByEmail(updateRequest.getEmail())) {
                throw new UserProfileException("El email ya está en uso");
            }
            user.setEmail(updateRequest.getEmail());
        }
        
        if (updateRequest.getFirstName() != null) {
            user.setFirstName(updateRequest.getFirstName());
        }
        
        if (updateRequest.getLastName() != null) {
            user.setLastName(updateRequest.getLastName());
        }
        
        try {
            // Guardar y devolver el usuario actualizado
            return userRepository.save(user);
        } catch (Exception e) {
            throw new UserProfileException("Error al guardar los cambios del perfil", e);
        }
    }
    
    /**
     * Cambia la contraseña de un usuario
     * @param username Nombre de usuario
     * @param changePasswordRequest Datos para el cambio de contraseña
     * @throws UserProfileException Si hay problemas al cambiar la contraseña
     */
    @Transactional
    public void changePassword(String username, ChangePasswordRequest changePasswordRequest) {
        User user = getUserByUsername(username);
        
        // Verificar que la contraseña actual sea correcta
        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
            throw new UserProfileException("La contraseña actual es incorrecta");
        }
        
        // Verificar que la nueva contraseña y la confirmación coincidan
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            throw new UserProfileException("La nueva contraseña y la confirmación no coinciden");
        }
        
        // Actualizar la contraseña
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new UserProfileException("Error al cambiar la contraseña", e);
        }
    }
}