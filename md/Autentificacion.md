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

## 3. Endpoints de Gestión de Perfiles (`/api/profile`)

Estos endpoints permiten a los usuarios autenticados gestionar su información personal. **Importante:** Los usuarios solo pueden editar su propio perfil y NO pueden cambiar sus roles.

*   **`GET /api/profile`**
    *   **Descripción:** Obtiene la información del perfil del usuario actualmente autenticado.
    *   **Headers:** `Authorization: Bearer <your_jwt_token>`
    *   **Response Body (Success - 200 OK):** `application/json` (Ver `AuthDTO.UserProfileResponse`)
        ```json
        {
          "id": long,
          "username": "string",
          "email": "string",
          "firstName": "string",
          "lastName": "string",
          "active": boolean
        }
        ```
    *   **Response Body (Error - 401 Unauthorized):** Si no se proporciona un token o es inválido.
    *   **Response Body (Error - 404 Not Found):** Si el usuario no existe.

*   **`PUT /api/profile`**
    *   **Descripción:** Actualiza la información del perfil del usuario autenticado. Todos los campos son opcionales, solo se actualizarán los campos proporcionados.
    *   **Headers:** `Authorization: Bearer <your_jwt_token>`
    *   **Request Body:** `application/json` (Ver `AuthDTO.UpdateProfileRequest`)
        ```json
        {
          "username": "string",    // Opcional: Mínimo 3, máximo 50 caracteres
          "email": "string",       // Opcional: Formato email válido, máximo 50 caracteres
          "firstName": "string",   // Opcional: Máximo 50 caracteres
          "lastName": "string"     // Opcional: Máximo 50 caracteres
        }
        ```
    *   **Response Body (Success - 200 OK):** `application/json` (Ver `AuthDTO.UserProfileResponse`)
        ```json
        {
          "id": long,
          "username": "string",
          "email": "string",
          "firstName": "string",
          "lastName": "string",
          "active": boolean
        }
        ```
    *   **Response Body (Error - 400 Bad Request):** Si los datos no cumplen las validaciones o si el username/email ya están en uso.
    *   **Response Body (Error - 401 Unauthorized):** Si no se proporciona un token o es inválido.

*   **`POST /api/profile/change-password`**
    *   **Descripción:** Cambia la contraseña del usuario autenticado. Requiere la contraseña actual para verificación.
    *   **Headers:** `Authorization: Bearer <your_jwt_token>`
    *   **Request Body:** `application/json` (Ver `AuthDTO.ChangePasswordRequest`)
        ```json
        {
          "currentPassword": "string",    // Requerido: Contraseña actual del usuario
          "newPassword": "string",        // Requerido: Nueva contraseña (mínimo 6, máximo 100 caracteres)
          "confirmPassword": "string"     // Requerido: Confirmación de la nueva contraseña
        }
        ```
    *   **Response Body (Success - 200 OK):** `application/json` (Ver `AuthDTO.MessageResponse`)
        ```json
        {
          "message": "Contraseña actualizada correctamente"
        }
        ```
    *   **Response Body (Error - 400 Bad Request):** Si la contraseña actual es incorrecta, las nuevas contraseñas no coinciden, o no cumplen las validaciones.
    *   **Response Body (Error - 401 Unauthorized):** Si no se proporciona un token o es inválido.

## 4. Autorización y Acceso a Endpoints

La `SecurityConfig.java` define las reglas de acceso a diferentes rutas basadas en los roles del usuario autenticado:

*   `/api/auth/**`: **Permitido para todos** (público).
*   `/api/public/**`: **Permitido para todos** (público).
*   `/api/admin/**`: Requiere el rol `ROLE_ADMIN`.
*   `/api/vendedores/**`: Requiere los roles `ROLE_ADMIN` o `ROLE_VENDEDOR`.
*   `/api/usuarios/**`: Requiere los roles `ROLE_ADMIN`, `ROLE_VENDEDOR` o `ROLE_USUARIO`.
*   `/api/profile/**`: Requiere los roles `ROLE_ADMIN`, `ROLE_VENDEDOR` o `ROLE_USUARIO`.
*   Cualquier otra ruta (`anyRequest()`): **Requiere autenticación** (un JWT válido).

### 4.1 Endpoints de Administración de Usuarios

Los siguientes endpoints están disponibles para la gestión de usuarios por parte de administradores:

*   **`GET /api/admin/usuarios`**
    *   **Descripción:** Obtiene la lista de todos los usuarios registrados en el sistema.
    *   **Acceso:** Solo usuarios con rol `ROLE_ADMIN`.
    *   **Response Body (Success - 200 OK):** Lista de usuarios con sus detalles.

*   **`GET /api/admin/usuarios/{id}`**
    *   **Descripción:** Obtiene los detalles de un usuario específico por su ID.
    *   **Acceso:** Solo usuarios con rol `ROLE_ADMIN`.
    *   **Response Body (Success - 200 OK):** Detalles del usuario.
    *   **Response Body (Error - 404 Not Found):** Si el usuario no existe.

*   **`PATCH /api/admin/usuarios/{id}/active`**
    *   **Descripción:** Activa o desactiva un usuario.
    *   **Acceso:** Solo usuarios con rol `ROLE_ADMIN`.
    *   **Request Param:** `active` (boolean) - Estado de activación.
    *   **Response Body (Success - 200 OK):** Mensaje de éxito.
    *   **Response Body (Error - 404 Not Found):** Si el usuario no existe.

*   **`POST /api/admin/usuarios/vendedores`**
    *   **Descripción:** Crea un nuevo usuario con rol de vendedor.
    *   **Acceso:** Solo usuarios con rol `ROLE_ADMIN`.
    *   **Request Body:** `application/json` (Ver `AuthDTO.CreateVendedorRequest`)
        ```json
        {
          "username": "string",    // Requerido: Mínimo 3, máximo 20 caracteres
          "email": "string",       // Requerido: Formato email válido, máximo 50 caracteres
          "password": "string",    // Requerido: Mínimo 6, máximo 40 caracteres
          "firstName": "string",   // Opcional: Máximo 50 caracteres
          "lastName": "string"     // Opcional: Máximo 50 caracteres
        }
        ```
    *   **Response Body (Success - 200 OK):** Detalles del vendedor creado.
    *   **Response Body (Error - 400 Bad Request):** Si los datos no cumplen las validaciones o si el username/email ya están en uso.

*   **`GET /api/admin/usuarios/vendedores`**
    *   **Descripción:** Obtiene la lista de todos los usuarios con rol de vendedor.
    *   **Acceso:** Solo usuarios con rol `ROLE_ADMIN`.
    *   **Response Body (Success - 200 OK):** Lista de vendedores con sus detalles.

## 5. Data Transfer Objects (DTOs) de Autenticación y Perfiles

Los DTOs se utilizan para estructurar los datos de entrada y salida de los endpoints de autenticación y gestión de perfiles (Ver `AuthDTO.java`).

### DTOs de Autenticación

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

*   **`CreateVendedorRequest`**
    *   `username`: `string` (Requerido, min 3, max 20)
    *   `email`: `string` (Requerido, formato email, max 50)
    *   `password`: `string` (Requerido, min 6, max 40)
    *   `firstName`: `string` (Opcional, max 50)
    *   `lastName`: `string` (Opcional, max 50)

*   **`JwtResponse`**
    *   `token`: `string` (El token JWT)
    *   `type`: `string` (Tipo de token, siempre "Bearer")
    *   `id`: `long` (ID del usuario)
    *   `username`: `string` (Nombre de usuario)
    *   `email`: `string` (Email del usuario)
    *   `roles`: `Set<String>` (Roles del usuario, ej: ["ROLE_USUARIO"])

### DTOs de Gestión de Perfiles

*   **`UserProfileResponse`**
    *   `id`: `long` (ID del usuario)
    *   `username`: `string` (Nombre de usuario)
    *   `email`: `string` (Email del usuario)
    *   `firstName`: `string` (Nombre del usuario)
    *   `lastName`: `string` (Apellido del usuario)
    *   `active`: `boolean` (Estado activo del usuario)

*   **`UpdateProfileRequest`**
    *   `username`: `string` (Opcional, min 3, max 50)
    *   `email`: `string` (Opcional, formato email, max 50)
    *   `firstName`: `string` (Opcional, max 50)
    *   `lastName`: `string` (Opcional, max 50)

*   **`ChangePasswordRequest`**
    *   `currentPassword`: `string` (Requerido)
    *   `newPassword`: `string` (Requerido, min 6, max 100)
    *   `confirmPassword`: `string` (Requerido)

### DTOs Comunes

*   **`MessageResponse`**
    *   `message`: `string` (Mensaje informativo o de error)

## 6. Guía de Implementación para Frontend

### Flujo de Autenticación Completo

1. **Registro de Usuario**
   ```javascript
   const registerUser = async (userData) => {
     const response = await fetch('http://localhost:8080/api/auth/signup', {
       method: 'POST',
       headers: {
         'Content-Type': 'application/json',
       },
       body: JSON.stringify({
         username: userData.username,
         email: userData.email,
         password: userData.password,
         firstName: userData.firstName,
         lastName: userData.lastName,
         roles: userData.roles || ['usuario']
       })
     });
     return await response.json();
   };
   ```

### Administración de Usuarios (Solo para Administradores)

1. **Crear un Nuevo Vendedor**
   ```javascript
   const createVendedor = async (vendedorData) => {
     const token = localStorage.getItem('authToken');
     const response = await fetch('http://localhost:8080/api/admin/usuarios/vendedores', {
       method: 'POST',
       headers: {
         'Authorization': `Bearer ${token}`,
         'Content-Type': 'application/json',
       },
       body: JSON.stringify({
         username: vendedorData.username,
         email: vendedorData.email,
         password: vendedorData.password,
         firstName: vendedorData.firstName,
         lastName: vendedorData.lastName
       })
     });
     
     if (response.ok) {
       return await response.json();
     }
     throw new Error('Failed to create vendor');
   };
   ```

2. **Obtener Lista de Vendedores**
   ```javascript
   const getVendedores = async () => {
     const token = localStorage.getItem('authToken');
     const response = await fetch('http://localhost:8080/api/admin/usuarios/vendedores', {
       method: 'GET',
       headers: {
         'Authorization': `Bearer ${token}`,
         'Content-Type': 'application/json',
       }
     });
     
     if (response.ok) {
       return await response.json();
     }
     throw new Error('Failed to fetch vendors');
   };
   ```

2. **Login de Usuario**
   ```javascript
   const loginUser = async (credentials) => {
     const response = await fetch('http://localhost:8080/api/auth/login', {
       method: 'POST',
       headers: {
         'Content-Type': 'application/json',
       },
       body: JSON.stringify({
         username: credentials.username,
         password: credentials.password
       })
     });

     if (response.ok) {
       const data = await response.json();
       // Guardar token en localStorage o estado global
       localStorage.setItem('authToken', data.token);
       localStorage.setItem('userInfo', JSON.stringify({
         id: data.id,
         username: data.username,
         email: data.email,
         roles: data.roles
       }));
       return data;
     }
     throw new Error('Login failed');
   };
   ```

3. **Obtener Perfil del Usuario**
   ```javascript
   const getUserProfile = async () => {
     const token = localStorage.getItem('authToken');
     const response = await fetch('http://localhost:8080/api/profile', {
       method: 'GET',
       headers: {
         'Authorization': `Bearer ${token}`,
         'Content-Type': 'application/json',
       }
     });

     if (response.ok) {
       return await response.json();
     }
     throw new Error('Failed to fetch profile');
   };
   ```

4. **Actualizar Perfil**
   ```javascript
   const updateProfile = async (profileData) => {
     const token = localStorage.getItem('authToken');
     const response = await fetch('http://localhost:8080/api/profile', {
       method: 'PUT',
       headers: {
         'Authorization': `Bearer ${token}`,
         'Content-Type': 'application/json',
       },
       body: JSON.stringify(profileData)
     });

     if (response.ok) {
       return await response.json();
     }
     throw new Error('Failed to update profile');
   };
   ```

5. **Cambiar Contraseña**
   ```javascript
   const changePassword = async (passwordData) => {
     const token = localStorage.getItem('authToken');
     const response = await fetch('http://localhost:8080/api/profile/change-password', {
       method: 'POST',
       headers: {
         'Authorization': `Bearer ${token}`,
         'Content-Type': 'application/json',
       },
       body: JSON.stringify({
         currentPassword: passwordData.currentPassword,
         newPassword: passwordData.newPassword,
         confirmPassword: passwordData.confirmPassword
       })
     });

     if (response.ok) {
       return await response.json();
     }
     throw new Error('Failed to change password');
   };
   ```

6. **Verificar Sesión**
   ```javascript
   const checkSession = async () => {
     const token = localStorage.getItem('authToken');
     if (!token) return null;

     const response = await fetch('http://localhost:8080/api/auth/session-info', {
       method: 'GET',
       headers: {
         'Authorization': `Bearer ${token}`,
         'Content-Type': 'application/json',
       }
     });

     if (response.ok) {
       return await response.json();
     }
     return null;
   };
   ```

7. **Logout**
   ```javascript
   const logoutUser = async () => {
     const token = localStorage.getItem('authToken');

     try {
       await fetch('http://localhost:8080/api/auth/logout', {
         method: 'POST',
         headers: {
           'Authorization': `Bearer ${token}`,
           'Content-Type': 'application/json',
         }
       });
     } finally {
       // Limpiar datos locales independientemente del resultado
       localStorage.removeItem('authToken');
       localStorage.removeItem('userInfo');
     }
   };
   ```

### Manejo de Errores y Validaciones

```javascript
const handleApiError = (error, response) => {
  if (response.status === 401) {
    // Token expirado o inválido
    localStorage.removeItem('authToken');
    localStorage.removeItem('userInfo');
    // Redirigir a login
    window.location.href = '/login';
  } else if (response.status === 400) {
    // Error de validación
    return error.message || 'Datos inválidos';
  } else if (response.status === 409) {
    // Conflicto (username/email ya en uso)
    return error.message || 'El usuario o email ya están en uso';
  }
  return 'Error del servidor';
};
```

### Interceptor para Requests Automáticos

```javascript
// Función helper para hacer requests autenticados
const authenticatedFetch = async (url, options = {}) => {
  const token = localStorage.getItem('authToken');

  const config = {
    ...options,
    headers: {
      'Content-Type': 'application/json',
      ...options.headers,
      ...(token && { 'Authorization': `Bearer ${token}` })
    }
  };

  const response = await fetch(url, config);

  if (response.status === 401) {
    // Token expirado, limpiar y redirigir
    localStorage.removeItem('authToken');
    localStorage.removeItem('userInfo');
    window.location.href = '/login';
    throw new Error('Session expired');
  }

  return response;
};
```

### Componente de Protección de Rutas (React)

```javascript
import React, { useEffect, useState } from 'react';

const ProtectedRoute = ({ children, requiredRoles = [] }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [userRoles, setUserRoles] = useState([]);

  useEffect(() => {
    const checkAuth = async () => {
      try {
        const sessionInfo = await checkSession();
        if (sessionInfo) {
          setIsAuthenticated(true);
          setUserRoles(sessionInfo.roles || []);
        }
      } catch (error) {
        setIsAuthenticated(false);
      } finally {
        setIsLoading(false);
      }
    };

    checkAuth();
  }, []);

  if (isLoading) {
    return <div>Cargando...</div>;
  }

  if (!isAuthenticated) {
    return <div>Acceso denegado. Por favor, inicia sesión.</div>;
  }

  if (requiredRoles.length > 0) {
    const hasRequiredRole = requiredRoles.some(role =>
      userRoles.includes(`ROLE_${role.toUpperCase()}`)
    );

    if (!hasRequiredRole) {
      return <div>No tienes permisos para acceder a esta página.</div>;
    }
  }

  return children;
};

export default ProtectedRoute;
```

## 7. Ejemplos para Flutter/Dart

### Servicio de Autenticación en Flutter

```dart
import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

class AuthService {
  static const String baseUrl = 'http://localhost:8080/api';

  // Registro de usuario
  Future<Map<String, dynamic>> register({
    required String username,
    required String email,
    required String password,
    String? firstName,
    String? lastName,
    List<String>? roles,
  }) async {
    final response = await http.post(
      Uri.parse('$baseUrl/auth/signup'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({
        'username': username,
        'email': email,
        'password': password,
        'firstName': firstName,
        'lastName': lastName,
        'roles': roles ?? ['usuario'],
      }),
    );

    return jsonDecode(response.body);
  }

  // Login de usuario
  Future<Map<String, dynamic>> login(String username, String password) async {
    final response = await http.post(
      Uri.parse('$baseUrl/auth/login'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({
        'username': username,
        'password': password,
      }),
    );

    if (response.statusCode == 200) {
      final data = jsonDecode(response.body);
      await _saveToken(data['token']);
      await _saveUserInfo(data);
      return data;
    } else {
      throw Exception('Login failed');
    }
  }

  // Obtener perfil del usuario
  Future<Map<String, dynamic>> getUserProfile() async {
    final token = await _getToken();
    final response = await http.get(
      Uri.parse('$baseUrl/profile'),
      headers: {
        'Authorization': 'Bearer $token',
        'Content-Type': 'application/json',
      },
    );

    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else if (response.statusCode == 401) {
      await logout();
      throw Exception('Session expired');
    } else {
      throw Exception('Failed to fetch profile');
    }
  }

  // Actualizar perfil
  Future<Map<String, dynamic>> updateProfile({
    String? username,
    String? email,
    String? firstName,
    String? lastName,
  }) async {
    final token = await _getToken();
    final body = <String, dynamic>{};

    if (username != null) body['username'] = username;
    if (email != null) body['email'] = email;
    if (firstName != null) body['firstName'] = firstName;
    if (lastName != null) body['lastName'] = lastName;

    final response = await http.put(
      Uri.parse('$baseUrl/profile'),
      headers: {
        'Authorization': 'Bearer $token',
        'Content-Type': 'application/json',
      },
      body: jsonEncode(body),
    );

    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception('Failed to update profile');
    }
  }

  // Cambiar contraseña
  Future<Map<String, dynamic>> changePassword({
    required String currentPassword,
    required String newPassword,
    required String confirmPassword,
  }) async {
    final token = await _getToken();
    final response = await http.post(
      Uri.parse('$baseUrl/profile/change-password'),
      headers: {
        'Authorization': 'Bearer $token',
        'Content-Type': 'application/json',
      },
      body: jsonEncode({
        'currentPassword': currentPassword,
        'newPassword': newPassword,
        'confirmPassword': confirmPassword,
      }),
    );

    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception('Failed to change password');
    }
  }

  // Logout
  Future<void> logout() async {
    final token = await _getToken();
    if (token != null) {
      try {
        await http.post(
          Uri.parse('$baseUrl/auth/logout'),
          headers: {
            'Authorization': 'Bearer $token',
            'Content-Type': 'application/json',
          },
        );
      } catch (e) {
        // Ignorar errores del logout en el servidor
      }
    }

    await _clearStorage();
  }

  // Verificar si el usuario está autenticado
  Future<bool> isAuthenticated() async {
    final token = await _getToken();
    if (token == null) return false;

    try {
      final response = await http.get(
        Uri.parse('$baseUrl/auth/session-info'),
        headers: {
          'Authorization': 'Bearer $token',
          'Content-Type': 'application/json',
        },
      );
      return response.statusCode == 200;
    } catch (e) {
      return false;
    }
  }

  // Métodos privados para manejo de storage
  Future<void> _saveToken(String token) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setString('auth_token', token);
  }

  Future<String?> _getToken() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString('auth_token');
  }

  Future<void> _saveUserInfo(Map<String, dynamic> userInfo) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setString('user_info', jsonEncode(userInfo));
  }

  Future<Map<String, dynamic>?> getUserInfo() async {
    final prefs = await SharedPreferences.getInstance();
    final userInfoString = prefs.getString('user_info');
    if (userInfoString != null) {
      return jsonDecode(userInfoString);
    }
    return null;
  }

  Future<void> _clearStorage() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.remove('auth_token');
    await prefs.remove('user_info');
  }
}
```

### Modelos de Datos para Flutter

```dart
// user_models.dart
class User {
  final int id;
  final String username;
  final String email;
  final String? firstName;
  final String? lastName;
  final bool active;
  final List<String> roles;

  User({
    required this.id,
    required this.username,
    required this.email,
    this.firstName,
    this.lastName,
    required this.active,
    required this.roles,
  });

  factory User.fromJson(Map<String, dynamic> json) {
    return User(
      id: json['id'],
      username: json['username'],
      email: json['email'],
      firstName: json['firstName'],
      lastName: json['lastName'],
      active: json['active'] ?? true,
      roles: List<String>.from(json['roles'] ?? []),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'username': username,
      'email': email,
      'firstName': firstName,
      'lastName': lastName,
      'active': active,
      'roles': roles,
    };
  }
}

class LoginRequest {
  final String username;
  final String password;

  LoginRequest({required this.username, required this.password});

  Map<String, dynamic> toJson() {
    return {
      'username': username,
      'password': password,
    };
  }
}

class UpdateProfileRequest {
  final String? username;
  final String? email;
  final String? firstName;
  final String? lastName;

  UpdateProfileRequest({
    this.username,
    this.email,
    this.firstName,
    this.lastName,
  });

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = {};
    if (username != null) data['username'] = username;
    if (email != null) data['email'] = email;
    if (firstName != null) data['firstName'] = firstName;
    if (lastName != null) data['lastName'] = lastName;
    return data;
  }
}
```

## 8. Mejores Prácticas y Consideraciones de Seguridad

### Seguridad del Token JWT

1. **Almacenamiento Seguro**:
   - En Flutter: Usar `flutter_secure_storage` en lugar de `SharedPreferences` para datos sensibles
   - En Web: Considerar usar `httpOnly` cookies en lugar de localStorage para mayor seguridad

2. **Expiración y Renovación**:
   - Implementar renovación automática de tokens antes de que expiren
   - Manejar correctamente la expiración de tokens

3. **Validación del Token**:
   - Verificar la validez del token en cada request importante
   - Implementar logout automático cuando el token expire

### Validaciones del Frontend

```javascript
// Validaciones de formulario
const validateEmail = (email) => {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email);
};

const validatePassword = (password) => {
  return password.length >= 6 && password.length <= 100;
};

const validateUsername = (username) => {
  return username.length >= 3 && username.length <= 50;
};

const validateProfileUpdate = (data) => {
  const errors = {};

  if (data.username && !validateUsername(data.username)) {
    errors.username = 'El nombre de usuario debe tener entre 3 y 50 caracteres';
  }

  if (data.email && !validateEmail(data.email)) {
    errors.email = 'Formato de email inválido';
  }

  if (data.firstName && data.firstName.length > 50) {
    errors.firstName = 'El nombre no puede exceder 50 caracteres';
  }

  if (data.lastName && data.lastName.length > 50) {
    errors.lastName = 'El apellido no puede exceder 50 caracteres';
  }

  return {
    isValid: Object.keys(errors).length === 0,
    errors
  };
};
```

### Manejo de Estados de Carga

```javascript
// Hook personalizado para manejo de estados (React)
import { useState, useCallback } from 'react';

const useApiCall = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const execute = useCallback(async (apiCall) => {
    try {
      setLoading(true);
      setError(null);
      const result = await apiCall();
      return result;
    } catch (err) {
      setError(err.message);
      throw err;
    } finally {
      setLoading(false);
    }
  }, []);

  return { loading, error, execute };
};

// Uso del hook
const ProfileComponent = () => {
  const { loading, error, execute } = useApiCall();
  const [profile, setProfile] = useState(null);

  const loadProfile = async () => {
    const profileData = await execute(() => getUserProfile());
    setProfile(profileData);
  };

  const updateProfile = async (data) => {
    const updatedProfile = await execute(() => updateProfile(data));
    setProfile(updatedProfile);
  };

  // ... resto del componente
};
```

---

## 📋 Resumen de Endpoints Disponibles

| Método | Endpoint | Descripción | Autenticación |
|--------|----------|-------------|---------------|
| POST | `/api/auth/signup` | Registro de usuario | No |
| POST | `/api/auth/login` | Login de usuario | No |
| GET | `/api/auth/session-info` | Información de sesión | Sí |
| POST | `/api/auth/logout` | Logout de usuario | Sí |
| GET | `/api/profile` | Obtener perfil | Sí |
| PUT | `/api/profile` | Actualizar perfil | Sí |
| POST | `/api/profile/change-password` | Cambiar contraseña | Sí |

---
