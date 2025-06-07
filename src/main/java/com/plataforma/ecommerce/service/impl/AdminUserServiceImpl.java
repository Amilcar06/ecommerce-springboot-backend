package com.plataforma.ecommerce.service.impl;

import com.plataforma.ecommerce.dto.AuthDTO.CreateVendedorRequest;
import com.plataforma.ecommerce.exception.UserProfileException;
import com.plataforma.ecommerce.model.entity.Role;
import com.plataforma.ecommerce.model.entity.User;
import com.plataforma.ecommerce.repository.RoleRepository;
import com.plataforma.ecommerce.repository.UserRepository;
import com.plataforma.ecommerce.service.IAdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class AdminUserServiceImpl implements IAdminUserService {

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
    @Override
    @Transactional
    public User createVendedor(CreateVendedorRequest request) throws UserProfileException {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserProfileException("El nombre de usuario ya está en uso");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserProfileException("El email ya está en uso");
        }

        User vendedor = new User();
        vendedor.setUsername(request.getUsername());
        vendedor.setEmail(request.getEmail());
        vendedor.setPassword(passwordEncoder.encode(request.getPassword()));
        vendedor.setFirstName(request.getFirstName());
        vendedor.setLastName(request.getLastName());
        vendedor.setActive(true);

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
