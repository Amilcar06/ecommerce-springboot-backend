package com.plataforma.ecommerce.service.impl;

import com.plataforma.ecommerce.dto.AuthDTO.ChangePasswordRequest;
import com.plataforma.ecommerce.dto.AuthDTO.UpdateProfileRequest;
import com.plataforma.ecommerce.exception.UserProfileException;
import com.plataforma.ecommerce.model.Usuario;
import com.plataforma.ecommerce.model.entity.User;
import com.plataforma.ecommerce.repository.UserRepository;
import com.plataforma.ecommerce.repository.UsuarioRepository;
import com.plataforma.ecommerce.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }

    @Override
    @Transactional
    public User updateProfile(String username, UpdateProfileRequest updateRequest) {
        User user = getUserByUsername(username);

        if (updateRequest.getUsername() != null && !updateRequest.getUsername().isEmpty()) {
            if (!username.equals(updateRequest.getUsername()) &&
                    userRepository.existsByUsername(updateRequest.getUsername())) {
                throw new UserProfileException("El nombre de usuario ya está en uso");
            }
            user.setUsername(updateRequest.getUsername());
        }

        if (updateRequest.getEmail() != null && !updateRequest.getEmail().isEmpty()) {
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
            User savedUser = userRepository.save(user);
            
            // Actualizar también el Usuario si existe
            usuarioRepository.findByUserId(user.getId()).ifPresent(usuario -> {
                usuario.setNombre(user.getFirstName() + " " + user.getLastName());
                usuario.setEmail(user.getEmail());
                usuarioRepository.save(usuario);
            });
            
            return savedUser;
        } catch (Exception e) {
            throw new UserProfileException("Error al guardar los cambios del perfil", e);
        }
    }

    @Override
    @Transactional
    public void changePassword(String username, ChangePasswordRequest changePasswordRequest) {
        User user = getUserByUsername(username);

        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
            throw new UserProfileException("La contraseña actual es incorrecta");
        }

        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            throw new UserProfileException("La nueva contraseña y la confirmación no coinciden");
        }

        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));

        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new UserProfileException("Error al cambiar la contraseña", e);
        }
    }
    
    @Override
    public Usuario getUsuarioByUsername(String username) {
        return usuarioRepository.findByUserUsername(username)
                .orElseGet(() -> {
                    // Si no existe, lo creamos automáticamente
                    User user = getUserByUsername(username);
                    return createUsuarioForUser(user);
                });
    }
    
    @Override
    @Transactional
    public Usuario createUsuarioForUser(User user) {
        // Verificar si ya existe un Usuario para este User
        return usuarioRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    // Crear un nuevo Usuario asociado al User
                    Usuario nuevoUsuario = new Usuario();
                    nuevoUsuario.setUser(user);
                    nuevoUsuario.setNombre(user.getFirstName() + " " + user.getLastName());
                    nuevoUsuario.setEmail(user.getEmail());
                    nuevoUsuario.setPedidos(new ArrayList<>());
                    return usuarioRepository.save(nuevoUsuario);
                });
    }
    
    @Override
    public Usuario getUsuarioByUserId(Long userId) {
        return usuarioRepository.findByUserId(userId)
                .orElseGet(() -> {
                    // Si no existe, lo creamos automáticamente
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new UsernameNotFoundException("User no encontrado con ID: " + userId));
                    return createUsuarioForUser(user);
                });
    }
}
