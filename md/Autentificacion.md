# Documentación de la API de Autenticación

Este documento describe los endpoints relacionados con la autenticación y autorización en la API de Ecommerce, cómo utilizarlos y los requisitos de seguridad.
## Implementacion de Autentificacion 
En [Aplicacion de Flutter de Ecommerce](https://github.com/BugaPunk/Ecommerce_App.git)
## 1. Configuración de Seguridad y Autenticación

La API utiliza Spring Security con JSON Web Tokens (JWT) para manejar la autenticación y autorización.

*   **Roles:** La aplicación define los siguientes roles de usuario (Ver `Role.java`):
    *   `ROLE_USUARIO`
    *   `ROLE_VENDEDOR`
    *   `ROLE_ADMIN`

*   **Inicialización de Datos:** La clase `DatabaseInitializer.java` se ejecuta al inicio de la aplicación para asegurar que los roles existan en la base de datos y para crear un usuario administrador por defecto si no hay ninguno (`username: admin`, `password: admin123`).

*   **Configuración de Seguridad:** La clase `SecurityConfig.java` configura Spring Security:
    *   Deshabilita CSRF y CORS (para permitir acceso desde cualquier origen, configurado en los controladores).
    *   Configura el manejo de excepciones para entradas no autorizadas (`JwtAuthenticationEntryPoint.java`).
    *   Establece la política de sesión como `STATELESS`, ya que la autenticación se basa en JWT.
    *   Define las reglas de autorización para los endpoints (Ver sección 3).
    *   Configura el filtro JWT (`JwtAuthenticationFilter.java`) para validar tokens en cada solicitud protegida.

*   **JWT:** La clase `JwtUtils.java` se encarga de generar y validar los tokens JWT utilizando una clave secreta y un tiempo de expiración definidos en `application.properties`.

*   **Servicio de Detalles de Usuario:** `UserDetailsServiceImpl.java` implementa `UserDetailsService` para cargar los detalles del usuario (incluyendo roles) desde la base de datos durante el proceso de autenticación.

## 2. Endpoints de Autenticación (`/api/auth`)

Estos endpoints permiten a los usuarios registrarse, iniciar sesión, obtener información de su sesión y cerrar sesión.

*   **`POST /api/auth/login`**
    *   **Descripción:** Autentica a un usuario utilizando su nombre de usuario y contraseña. Si las credenciales son válidas, devuelve un JWT que debe ser incluido en las solicitudes posteriores a endpoints protegidos.
    *   **Request Body:** `application/json` (Ver `AuthDTO.LoginRequest`)
        ```json
        {
          "username": "string",
          "password": "string"
        }
        ```
    *   **Response Body (Success - 200 OK):** `application/json` (Ver `AuthDTO.JwtResponse`)
        ```json
        {
          "token": "string",       // JWT para usar en el header Authorization
          "type": "Bearer",
          "id": long,
          "username": "string",
          "email": "string",
          "roles": ["string"]      // Lista de roles del usuario (ej: "ROLE_USUARIO")
        }
        ```
    *   **Response Body (Error - 401 Unauthorized):** Si las credenciales son incorrectas.
    *   **Response Body (Error - 400 Bad Request):** Si el request body no cumple con las validaciones (`@NotBlank`).

*   **`POST /api/auth/signup`**
    *   **Descripción:** Registra un nuevo usuario en el sistema. Permite asignar roles opcionalmente (por defecto es `ROLE_USUARIO`).
    *   **Request Body:** `application/json` (Ver `AuthDTO.SignupRequest`)
        ```json
        {
          "username": "string",    // Mínimo 3, máximo 20 caracteres
          "email": "string",       // Formato email, máximo 50 caracteres
          "password": "string",    // Mínimo 6, máximo 40 caracteres
          "firstName": "string",   // Opcional
          "lastName": "string",    // Opcional
          "roles": ["string"]      // Opcional: Lista de roles ("admin", "vendedor", "usuario"). Por defecto es ["usuario"].
        }
        ```
    *   **Response Body (Success - 200 OK):** `application/json` (Ver `AuthDTO.MessageResponse`)
        ```json
        {
          "message": "User registered successfully!"
        }
        ```
    *   **Response Body (Error - 400 Bad Request):** Si el username o email ya están en uso, o si el request body no cumple con las validaciones (`@NotBlank`, `@Size`, `@Email`).

*   **`GET /api/auth/session-info`**
    *   **Descripción:** Obtiene la información del usuario actualmente autenticado. Requiere un JWT válido en el header `Authorization`.
    *   **Headers:** `Authorization: Bearer <your_jwt_token>`
    *   **Response Body (Success - 200 OK):** `application/json` (Ver `AuthDTO.JwtResponse`)
        ```json
        {
          "token": null,           // No se genera un nuevo token en este endpoint
          "type": "Bearer",
          "id": long,
          "username": "string",
          "email": "string",
          "roles": ["string"]      // Lista de roles del usuario
        }
        ```
    *   **Response Body (Success - 200 OK):** `application/json` (Ver `AuthDTO.MessageResponse`) si no hay sesión activa o el token es inválido/expirado.
        ```json
        {
          "message": "No active session"
        }
        ```
    *   **Response Body (Error - 401 Unauthorized):** Si no se proporciona un token o es inválido (aunque el endpoint devuelve 200 con mensaje si no hay sesión, el filtro JWT podría interceptar y devolver 401 si el token es inválido).

*   **`POST /api/auth/logout`**
    *   **Descripción:** Cierra la sesión del usuario actual invalidando el contexto de seguridad. Requiere un JWT válido en el header `Authorization`.
    *   **Headers:** `Authorization: Bearer <your_jwt_token>`
    *   **Response Body (Success - 200 OK):** `application/json` (Ver `AuthDTO.MessageResponse`)
        ```json
        {
          "message": "Logged out successfully!"
        }
        ```
    *   **Response Body (Error - 401 Unauthorized):** Si no se proporciona un token o es inválido.

## 3. Autorización y Acceso a Endpoints

La `SecurityConfig.java` define las reglas de acceso a diferentes rutas basadas en los roles del usuario autenticado:

*   `/api/auth/**`: **Permitido para todos** (público).
*   `/api/public/**`: **Permitido para todos** (público).
*   `/api/admin/**`: Requiere el rol `ROLE_ADMIN`.
*   `/api/vendedores/**`: Requiere los roles `ROLE_ADMIN` o `ROLE_VENDEDOR`.
*   `/api/usuarios/**`: Requiere los roles `ROLE_ADMIN`, `ROLE_VENDEDOR` o `ROLE_USUARIO`.
*   Cualquier otra ruta (`anyRequest()`): **Requiere autenticación** (un JWT válido).

## 4. Data Transfer Objects (DTOs) de Autenticación

Los DTOs se utilizan para estructurar los datos de entrada y salida de los endpoints de autenticación (Ver `AuthDTO.java`).

*   **`LoginRequest`**
    *   `username`: `string` (Requerido)
    *   `password`: `string` (Requerido)

*   **`SignupRequest`**
    *   `username`: `string` (Requerido, min 3, max 20)
    *   `email`: `string` (Requerido, formato email, max 50)
    *   `password`: `string` (Requerido, min 6, max 40)
    *   `firstName`: `string` (Opcional)
    *   `lastName`: `string` (Opcional)
    *   `roles`: `Set<String>` (Opcional, ej: ["admin", "vendedor", "usuario"])

*   **`JwtResponse`**
    *   `token`: `string` (El token JWT)
    *   `type`: `string` (Tipo de token, siempre "Bearer")
    *   `id`: `long` (ID del usuario)
    *   `username`: `string` (Nombre de usuario)
    *   `email`: `string` (Email del usuario)
    *   `roles`: `Set<String>` (Roles del usuario, ej: ["ROLE_USUARIO"])

*   **`MessageResponse`**
    *   `message`: `string` (Mensaje informativo o de error)

---
