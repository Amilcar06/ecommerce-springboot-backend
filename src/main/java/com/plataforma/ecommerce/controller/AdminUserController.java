package com.plataforma.ecommerce.controller;

import com.plataforma.ecommerce.dto.AuthDTO.CreateVendedorRequest;
import com.plataforma.ecommerce.dto.AuthDTO.MessageResponse;
import com.plataforma.ecommerce.exception.UserProfileException;
import com.plataforma.ecommerce.model.entity.Role;
import com.plataforma.ecommerce.model.entity.User;
import com.plataforma.ecommerce.repository.UserRepository;
import com.plataforma.ecommerce.service.AdminUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/usuarios")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminUserController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AdminUserService adminUserService;

    /**
     * Obtiene la lista de todos los usuarios
     * @return Lista de usuarios
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        List<UserSummaryResponse> users = userRepository.findAll().stream()
                .map(user -> new UserSummaryResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.isActive(),
                        user.getRoles().stream()
                                .map(role -> role.getName().name())
                                .collect(Collectors.toSet())
                ))
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(users);
    }

    /**
     * Obtiene un usuario por su ID
     * @param id ID del usuario
     * @return Datos del usuario
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserProfileException("Usuario no encontrado con ID: " + id));
        
        return ResponseEntity.ok(new UserDetailResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.isActive(),
                user.getRoles().stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.toSet())
        ));
    }

    /**
     * Activa o desactiva un usuario
     * @param id ID del usuario
     * @param active Estado de activación
     * @return Mensaje de éxito
     */
    @PatchMapping("/{id}/active")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> setUserActiveStatus(
            @PathVariable Long id,
            @RequestParam boolean active) {
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserProfileException("Usuario no encontrado con ID: " + id));
        
        user.setActive(active);
        userRepository.save(user);
        
        return ResponseEntity.ok(new MessageResponse(
                "Estado de usuario actualizado correctamente a: " + (active ? "activo" : "inactivo")));
    }
    
    /**
     * Crea un nuevo usuario con rol de vendedor
     * @param request Datos del nuevo vendedor
     * @return Mensaje de éxito
     */
    @PostMapping("/vendedores")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createVendedor(@Valid @RequestBody CreateVendedorRequest request) {
        User vendedor = adminUserService.createVendedor(request);
        
        return ResponseEntity.ok(new UserDetailResponse(
                vendedor.getId(),
                vendedor.getUsername(),
                vendedor.getEmail(),
                vendedor.getFirstName(),
                vendedor.getLastName(),
                vendedor.isActive(),
                vendedor.getRoles().stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.toSet())
        ));
    }
    
    /**
     * Obtiene la lista de todos los vendedores
     * @return Lista de vendedores
     */
    @GetMapping("/vendedores")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getAllVendedores() {
        List<UserSummaryResponse> vendedores = userRepository.findAll().stream()
                .filter(user -> user.getRoles().stream()
                        .anyMatch(role -> role.getName() == Role.RoleName.ROLE_VENDEDOR))
                .map(user -> new UserSummaryResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.isActive(),
                        user.getRoles().stream()
                                .map(role -> role.getName().name())
                                .collect(Collectors.toSet())
                ))
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(vendedores);
    }

    /**
     * Clase interna para representar un resumen de usuario en la lista
     */
    private static class UserSummaryResponse {
        private Long id;
        private String username;
        private String email;
        private String firstName;
        private String lastName;
        private boolean active;
        private Set<String> roles;
        
        public UserSummaryResponse(Long id, String username, String email,
                                  String firstName, String lastName, boolean active,
                                  Set<String> roles) {
            this.id = id;
            this.username = username;
            this.email = email;
            this.firstName = firstName;
            this.lastName = lastName;
            this.active = active;
            this.roles = roles;
        }

        public Long getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public boolean isActive() {
            return active;
        }

        public Set<String> getRoles() {
            return roles;
        }
    }

    /**
     * Clase interna para representar los detalles completos de un usuario
     */
    private static class UserDetailResponse extends UserSummaryResponse {
        public UserDetailResponse(Long id, String username, String email,
                                 String firstName, String lastName, boolean active,
                                 Set<String> roles) {
            super(id, username, email, firstName, lastName, active, roles);
        }
    }
}