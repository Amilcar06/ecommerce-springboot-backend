package com.plataforma.ecommerce.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API E-commerce", 
                version = "1.0", 
                description = "Documentación de la API de E-commerce con protección por roles\n\n" +
                        "**Roles disponibles:**\n" +
                        "- **ROLE_ADMIN**: Acceso completo, puede crear tiendas y categorías\n" +
                        "- **ROLE_VENDEDOR**: Puede gestionar productos y funciones de usuario\n" +
                        "- **ROLE_USUARIO**: Puede usar carrito, pedidos, pagos y reseñas\n\n" +
                        "**Para probar:**\n" +
                        "1. Registra un usuario en `/api/auth/signup`\n" +
                        "2. Inicia sesión en `/api/auth/login` para obtener el token\n" +
                        "3. Haz clic en 'Authorize' y pega el token (sin 'Bearer ')\n" +
                        "4. Prueba los endpoints según tu rol"
        ),
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {
    // No necesita más código por ahora
}
