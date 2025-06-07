package com.plataforma.ecommerce.service.impl;

import com.plataforma.ecommerce.dto.AuthDTO.ChangePasswordRequest;
import com.plataforma.ecommerce.dto.AuthDTO.UpdateProfileRequest;
import com.plataforma.ecommerce.exception.UserProfileException;
import com.plataforma.ecommerce.model.entity.User;
import com.plataforma.ecommerce.repository.UserRepository;
import com.plataforma.ecommerce.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

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
            return userRepository.save(user);
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
}
