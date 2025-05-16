package com.plataforma.ecommerce.config;


import com.bugapunks.ecommerce.model.entity.Role;
import com.bugapunks.ecommerce.model.entity.User;
import com.bugapunks.ecommerce.repository.RoleRepository;
import com.bugapunks.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Initialize roles if they don't exist
        initializeRoles();

        // Create default admin user if it doesn't exist
        createDefaultAdmin();
    }

    private void initializeRoles() {
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setName(Role.RoleName.ROLE_ADMIN);
            roleRepository.save(adminRole);

            Role vendedorRole = new Role();
            vendedorRole.setName(Role.RoleName.ROLE_VENDEDOR);
            roleRepository.save(vendedorRole);

            Role usuarioRole = new Role();
            usuarioRole.setName(Role.RoleName.ROLE_USUARIO);
            roleRepository.save(usuarioRole);

            System.out.println("Roles inicializados en la base de datos");
        }
    }

    private void createDefaultAdmin() {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@ecommerce.com");
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setActive(true);

            Set<Role> roles = new HashSet<>();
            Role adminRole = roleRepository.findByName(Role.RoleName.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role not found."));
            roles.add(adminRole);
            admin.setRoles(roles);

            userRepository.save(admin);
            System.out.println("Admin user created: admin / admin123");
        }
    }
}
