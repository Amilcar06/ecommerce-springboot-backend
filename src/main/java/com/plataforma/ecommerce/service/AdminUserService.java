package com.plataforma.ecommerce.service;

import com.plataforma.ecommerce.dto.AuthDTO.CreateVendedorRequest;
import com.plataforma.ecommerce.exception.UserProfileException;
import com.plataforma.ecommerce.model.entity.Role;
import com.plataforma.ecommerce.model.entity.User;
import com.plataforma.ecommerce.repository.RoleRepository;
import com.plataforma.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class AdminUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Crea un nuevo usuario con rol de vendedor
     * @param request Datos del nuevo vendedor
     * @return Usuario creado
     * @throws UserProfileException Si hay problemas al crear el usuario
     */
    @Transactional
    public User createVendedor(CreateVendedorRequest request) {
        // Verificar que el username no exista
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserProfileException("El nombre de usuario ya está en uso");
        }

        // Verificar que el email no exista
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserProfileException("El email ya está en uso");
        }

        // Crear el nuevo usuario
        User vendedor = new User();
        vendedor.setUsername(request.getUsername());
        vendedor.setEmail(request.getEmail());
        vendedor.setPassword(passwordEncoder.encode(request.getPassword()));
        vendedor.setFirstName(request.getFirstName());
        vendedor.setLastName(request.getLastName());
        vendedor.setActive(true);

        // Asignar rol de vendedor
        Set<Role> roles = new HashSet<>();
        Role vendedorRole = roleRepository.findByName(Role.RoleName.ROLE_VENDEDOR)
                .orElseThrow(() -> new UserProfileException("Error: Rol de vendedor no encontrado"));
        roles.add(vendedorRole);
        vendedor.setRoles(roles);

        try {
            return userRepository.save(vendedor);
        } catch (Exception e) {
            throw new UserProfileException("Error al crear el usuario vendedor", e);
        }
    }
}