package com.plataforma.ecommerce.controller;

import com.plataforma.ecommerce.dto.AuthDTO.ChangePasswordRequest;
import com.plataforma.ecommerce.dto.AuthDTO.MessageResponse;
import com.plataforma.ecommerce.dto.AuthDTO.UpdateProfileRequest;
import com.plataforma.ecommerce.dto.AuthDTO.UserProfileResponse;
import com.plataforma.ecommerce.model.entity.User;
import com.plataforma.ecommerce.service.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@Tag(name = "Perfil de Usuario", description = "Operaciones relacionadas con perfil de usuario")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserProfileController {

    @Autowired
    private IUserService userService;

    /**
     * Obtiene el perfil del usuario actualmente autenticado
     * @return Datos del perfil del usuario
     */
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR', 'ROLE_USUARIO')")
    public ResponseEntity<?> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        User user = userService.getUserByUsername(username);
        
        // Crear un objeto con la información que queremos devolver
        // Excluimos información sensible como la contraseña
        return ResponseEntity.ok(new UserProfileResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            user.isActive()
        ));
    }

    /**
     * Actualiza el perfil del usuario actualmente autenticado
     * @param updateRequest Datos a actualizar
     * @return Mensaje de éxito o error
     */
    @PutMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR', 'ROLE_USUARIO')")
    public ResponseEntity<?> updateUserProfile(@Valid @RequestBody UpdateProfileRequest updateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        User updatedUser = userService.updateProfile(username, updateRequest);
        
        return ResponseEntity.ok(new UserProfileResponse(
            updatedUser.getId(),
            updatedUser.getUsername(),
            updatedUser.getEmail(),
            updatedUser.getFirstName(),
            updatedUser.getLastName(),
            updatedUser.isActive()
        ));
    }
    
    /**
     * Cambia la contraseña del usuario actualmente autenticado
     * @param changePasswordRequest Datos para el cambio de contraseña
     * @return Mensaje de éxito
     */
    @PostMapping("/change-password")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR', 'ROLE_USUARIO')")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        userService.changePassword(username, changePasswordRequest);

        return ResponseEntity.ok(new MessageResponse("Contraseña actualizada correctamente"));
    }
}