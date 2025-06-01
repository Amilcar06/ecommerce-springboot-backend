package com.plataforma.ecommerce.dto;

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
    public static class LoginRequest {
        @NotBlank
        private String username;

        @NotBlank
        private String password;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignupRequest {
        @NotBlank
        @Size(min = 3, max = 20)
        private String username;

        @NotBlank
        @Size(max = 50)
        @Email
        private String email;

        @NotBlank
        @Size(min = 6, max = 40)
        private String password;

        private String firstName;
        
        private String lastName;
        
        private Set<String> roles;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateVendedorRequest {
        @NotBlank(message = "El nombre de usuario es obligatorio")
        @Size(min = 3, max = 20, message = "El nombre de usuario debe tener entre 3 y 20 caracteres")
        private String username;

        @NotBlank(message = "El email es obligatorio")
        @Size(max = 50, message = "El email no debe exceder los 50 caracteres")
        @Email(message = "Debe proporcionar un email válido")
        private String email;

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 6, max = 40, message = "La contraseña debe tener entre 6 y 40 caracteres")
        private String password;

        @Size(max = 50, message = "El nombre no debe exceder los 50 caracteres")
        private String firstName;
        
        @Size(max = 50, message = "El apellido no debe exceder los 50 caracteres")
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
    public static class UpdateProfileRequest {
        @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
        private String username;

        @Size(max = 50)
        @Email(message = "Debe proporcionar un email válido")
        private String email;

        @Size(max = 50)
        private String firstName;

        @Size(max = 50)
        private String lastName;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChangePasswordRequest {
        @NotBlank(message = "La contraseña actual es obligatoria")
        private String currentPassword;

        @NotBlank(message = "La nueva contraseña es obligatoria")
        @Size(min = 6, max = 100, message = "La nueva contraseña debe tener entre 6 y 100 caracteres")
        private String newPassword;

        @NotBlank(message = "La confirmación de contraseña es obligatoria")
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
