package com.plataforma.ecommerce.config;


import com.plataforma.ecommerce.model.entity.User;
import com.plataforma.ecommerce.model.entity.Role;
import com.plataforma.ecommerce.model.Tienda;
import com.plataforma.ecommerce.model.Categoria;
import com.plataforma.ecommerce.repository.RoleRepository;
import com.plataforma.ecommerce.repository.UserRepository;
import com.plataforma.ecommerce.repository.TiendaRepository;
import com.plataforma.ecommerce.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
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
    
    @Autowired
    private TiendaRepository tiendaRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        // Initialize roles if they don't exist
        initializeRoles();

        // Create default admin user if it doesn't exist
        createDefaultAdmin();
        
        // Create additional admin user if it doesn't exist
        createAdditionalAdmin();
        
        // Eliminar todas las tiendas y categorías existentes y reiniciar secuencias
        resetTiendasYCategorias();
        
        // Create default store if it doesn't exist
        createDefaultStore();
        
        // Create default categories if they don't exist
        createDefaultCategories();
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
                    .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
            roles.add(adminRole);
            admin.setRoles(roles);

            userRepository.save(admin);
            System.out.println("Usuario admin creado: admin / admin123");
        }
    }
    
    private void createAdditionalAdmin() {
        if (!userRepository.existsByUsername("adminUser")) {
            User admin = new User();
            admin.setUsername("adminUser");
            admin.setPassword(passwordEncoder.encode("admin456"));
            admin.setEmail("admin2@ecommerce.com");
            admin.setFirstName("Admin");
            admin.setLastName("Secundario");
            admin.setActive(true);

            Set<Role> roles = new HashSet<>();
            Role adminRole = roleRepository.findByName(Role.RoleName.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
            roles.add(adminRole);
            admin.setRoles(roles);

            userRepository.save(admin);
            System.out.println("Usuario admin adicional creado: adminUser / admin456");
        }
    }
    
    private void createDefaultStore() {
        if (tiendaRepository.count() == 0) {
            Tienda tienda = new Tienda();
            tienda.setCodigoTienda("STORE001");
            tienda.setNombre("Tienda Principal");
            tienda.setDescripcion("Tienda principal del ecommerce");
            tienda.setCalificacionPromedio(0.0);
            
            tiendaRepository.save(tienda);
            System.out.println("Tienda por defecto creada: Tienda Principal");
        }
    }
    
    private void createDefaultCategories() {
        if (categoriaRepository.count() == 0) {
            // Obtener la tienda por defecto
            Tienda tiendaDefault = tiendaRepository.findByCodigoTienda("STORE001")
                    .orElseThrow(() -> new RuntimeException("Error: Tienda por defecto no encontrada."));
            
            // Crear categorías
            String[] categorias = {"Electrónicos", "Moda", "Hogar", "Deportes", "Libros"};
            
            for (String nombreCategoria : categorias) {
                Categoria categoria = new Categoria();
                categoria.setNombre(nombreCategoria);
                categoria.setTienda(tiendaDefault);
                
                categoriaRepository.save(categoria);
                System.out.println("Categoría creada: " + nombreCategoria);
            }
            
            System.out.println("Categorías por defecto inicializadas en la base de datos");
        }
    }
    
    private void resetTiendasYCategorias() {
        try {
            // Verificar si las tablas existen antes de intentar truncarlas
            boolean categoriaExists = tableExists("categoria");
            boolean tiendaExists = tableExists("tienda");
            
            if (categoriaExists) {
                System.out.println("Eliminando todas las categorías existentes...");
                jdbcTemplate.execute("TRUNCATE TABLE categoria CASCADE");
                
                // Verificar si la secuencia existe antes de reiniciarla
                if (sequenceExists("categoria_id_seq")) {
                    System.out.println("Reiniciando secuencias de ID para categorías...");
                    jdbcTemplate.execute("ALTER SEQUENCE categoria_id_seq RESTART WITH 1");
                } else {
                    System.out.println("La secuencia categoria_id_seq no existe.");
                }
            } else {
                System.out.println("La tabla categoria no existe.");
            }
            
            if (tiendaExists) {
                System.out.println("Eliminando todas las tiendas existentes...");
                jdbcTemplate.execute("TRUNCATE TABLE tienda CASCADE");
                
                // Verificar si la secuencia existe antes de reiniciarla
                if (sequenceExists("tienda_id_seq")) {
                    System.out.println("Reiniciando secuencias de ID para tiendas...");
                    jdbcTemplate.execute("ALTER SEQUENCE tienda_id_seq RESTART WITH 1");
                } else {
                    System.out.println("La secuencia tienda_id_seq no existe.");
                }
            } else {
                System.out.println("La tabla tienda no existe.");
            }
            
            System.out.println("Proceso de limpieza de tiendas y categorías completado.");
        } catch (Exception e) {
            System.err.println("Error al resetear tiendas y categorías: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Método para verificar si una tabla existe
    private boolean tableExists(String tableName) {
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = ? AND table_schema = 'public'",
            Integer.class,
            tableName
        );
        return count != null && count > 0;
    }
    
    // Método para verificar si una secuencia existe
    private boolean sequenceExists(String sequenceName) {
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM information_schema.sequences WHERE sequence_name = ? AND sequence_schema = 'public'",
            Integer.class,
            sequenceName
        );
        return count != null && count > 0;
    }
}
