package com.plataforma.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

public class AuthDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "Datos para iniciar sesión")
    public static class LoginRequest {
        @NotBlank
        @Schema(description = "Nombre de usuario", example = "usuario_test")
        private String username;

        @NotBlank
        @Schema(description = "Contraseña", example = "password123")
        private String password;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "Datos para registrar un nuevo usuario")
    public static class SignupRequest {
        @NotBlank
        @Size(min = 3, max = 20)
        @Schema(description = "Nombre de usuario único", example = "usuario_test")
        private String username;

        @NotBlank
        @Size(max = 50)
        @Email
        @Schema(description = "Correo electrónico", example = "usuario@test.com")
        private String email;

        @NotBlank
        @Size(min = 6, max = 40)
        @Schema(description = "Contraseña", example = "password123")
        private String password;

        @Schema(description = "Nombre", example = "Juan")
        private String firstName;

        @Schema(description = "Apellido", example = "Pérez")
        private String lastName;

        @Schema(description = "Roles del usuario", example = "[\"ROLE_USUARIO\"]", 
                allowableValues = {"ROLE_USUARIO", "ROLE_VENDEDOR", "ROLE_ADMIN"})
        private Set<String> roles;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "Datos para crear un vendedor (solo administradores)")
    public static class CreateVendedorRequest {
        @NotBlank(message = "El nombre de usuario es obligatorio")
        @Size(min = 3, max = 20, message = "El nombre de usuario debe tener entre 3 y 20 caracteres")
        @Schema(description = "Nombre de usuario único", example = "vendedor_test")
        private String username;

        @NotBlank(message = "El email es obligatorio")
        @Size(max = 50, message = "El email no debe exceder los 50 caracteres")
        @Email(message = "Debe proporcionar un email válido")
        @Schema(description = "Correo electrónico", example = "vendedor@test.com")
        private String email;

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 6, max = 40, message = "La contraseña debe tener entre 6 y 40 caracteres")
        @Schema(description = "Contraseña", example = "password123")
        private String password;

        @Size(max = 50, message = "El nombre no debe exceder los 50 caracteres")
        @Schema(description = "Nombre", example = "Carlos")
        private String firstName;

        @Size(max = 50, message = "El apellido no debe exceder los 50 caracteres")
        @Schema(description = "Apellido", example = "Vendedor")
        private String lastName;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JwtResponse {
        private String token;
        private String type = "Bearer";
        private Long id;
        private String username;
        private String email;
        private Set<String> roles;

        public JwtResponse(String token, Long id, String username, String email, Set<String> roles) {
            this.token = token;
            this.id = id;
            this.username = username;
            this.email = email;
            this.roles = roles;
        }
    }

    @Data
    @NoArgsConstructor
    public static class MessageResponse {
        private String message;

        public MessageResponse(String message) {
            this.message = message;
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "Datos para actualizar el perfil del usuario")
    public static class UpdateProfileRequest {
        @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
        @Schema(description = "Nuevo nombre de usuario", example = "usuario_actualizado")
        private String username;

        @Size(max = 50)
        @Email(message = "Debe proporcionar un email válido")
        @Schema(description = "Nuevo correo electrónico", example = "nuevo@email.com")
        private String email;

        @Size(max = 50)
        @Schema(description = "Nuevo nombre", example = "Juan Carlos")
        private String firstName;

        @Size(max = 50)
        @Schema(description = "Nuevo apellido", example = "Pérez García")
        private String lastName;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "Datos para cambiar la contraseña")
    public static class ChangePasswordRequest {
        @NotBlank(message = "La contraseña actual es obligatoria")
        @Schema(description = "Contraseña actual", example = "password123")
        private String currentPassword;

        @NotBlank(message = "La nueva contraseña es obligatoria")
        @Size(min = 6, max = 100, message = "La nueva contraseña debe tener entre 6 y 100 caracteres")
        @Schema(description = "Nueva contraseña", example = "newpassword456")
        private String newPassword;

        @NotBlank(message = "La confirmación de contraseña es obligatoria")
        @Schema(description = "Confirmación de la nueva contraseña", example = "newpassword456")
        private String confirmPassword;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserProfileResponse {
        private Long id;
        private String username;
        private String email;
        private String firstName;
        private String lastName;
        private boolean active;
    }
}
