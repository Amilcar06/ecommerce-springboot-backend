# Documentación de la API de E-commerce

Esta documentación proporciona detalles sobre todos los endpoints disponibles en la API de E-commerce, organizados por categorías para facilitar su implementación en el frontend.

## Índice
- [Autenticación](#autenticación)
- [Perfil de Usuario](#perfil-de-usuario)
- [Administración de Usuarios](#administración-de-usuarios)
- [Productos](#productos)
- [Categorías](#categorías)
- [Tiendas](#tiendas)
- [Carritos](#carritos)
- [Pedidos](#pedidos)
- [Pagos](#pagos)
- [Facturas](#facturas)
- [Reseñas de Productos](#reseñas-de-productos)
- [Reseñas de Tiendas](#reseñas-de-tiendas)
- [Endpoints de Prueba](#endpoints-de-prueba)

## Autenticación

### Registro de Usuario

**Endpoint:** `POST /api/auth/signup`

**Descripción:** Registra un nuevo usuario en el sistema.

**Cuerpo de la solicitud:**
```json
{
  "username": "usuario1",
  "email": "usuario1@example.com",
  "password": "password123",
  "firstName": "Nombre",
  "lastName": "Apellido",
  "role": ["ROLE_USUARIO"]
}
```

**Respuesta exitosa:**
```json
{
  "message": "Usuario registrado exitosamente!"
}
```

### Inicio de Sesión

**Endpoint:** `POST /api/auth/login`

**Descripción:** Autentica a un usuario y devuelve un token JWT.

**Cuerpo de la solicitud:**
```json
{
  "username": "usuario1",
  "password": "password123"
}
```

**Respuesta exitosa:**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "type": "Bearer",
  "id": 1,
  "username": "usuario1",
  "email": "usuario1@example.com",
  "roles": ["ROLE_USUARIO"]
}
```

### Información de Sesión

**Endpoint:** `GET /api/auth/session-info`

**Descripción:** Obtiene información sobre la sesión actual del usuario autenticado.

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Respuesta exitosa (usuario autenticado):**
```json
{
  "token": null,
  "type": "Bearer",
  "id": 1,
  "username": "usuario1",
  "email": "usuario1@example.com",
  "roles": ["ROLE_USUARIO"]
}
```

**Respuesta exitosa (sin sesión activa):**
```json
{
  "message": "No active session"
}
```

### Cerrar Sesión

**Endpoint:** `POST /api/auth/logout`

**Descripción:** Cierra la sesión del usuario actual.

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Respuesta exitosa:**
```json
{
  "message": "Logged out successfully!"
}
```

## Perfil de Usuario

### Obtener Perfil de Usuario

**Endpoint:** `GET /api/profile`

**Descripción:** Obtiene el perfil del usuario autenticado.

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Respuesta exitosa:**
```json
{
  "id": 1,
  "username": "usuario1",
  "email": "usuario1@example.com",
  "firstName": "Nombre",
  "lastName": "Apellido",
  "active": true
}
```

### Actualizar Perfil de Usuario

**Endpoint:** `PUT /api/profile`

**Descripción:** Actualiza el perfil del usuario autenticado.

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Cuerpo de la solicitud:**
```json
{
  "firstName": "Nuevo Nombre",
  "lastName": "Nuevo Apellido",
  "email": "nuevo_email@example.com"
}
```

**Respuesta exitosa:**
```json
{
  "id": 1,
  "username": "usuario1",
  "email": "nuevo_email@example.com",
  "firstName": "Nuevo Nombre",
  "lastName": "Nuevo Apellido",
  "active": true
}
```

### Cambiar Contraseña

**Endpoint:** `POST /api/profile/change-password`

**Descripción:** Cambia la contraseña del usuario autenticado.

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Cuerpo de la solicitud:**
```json
{
  "currentPassword": "password123",
  "newPassword": "newPassword123",
  "confirmPassword": "newPassword123"
}
```

**Respuesta exitosa:**
```json
{
  "message": "Contraseña actualizada correctamente"
}
```

## Administración de Usuarios

### Listar Todos los Usuarios

**Endpoint:** `GET /api/admin/usuarios`

**Descripción:** Obtiene una lista de todos los usuarios (solo para administradores).

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Respuesta exitosa:**
```json
[
  {
    "id": 1,
    "username": "usuario1",
    "email": "usuario1@example.com",
    "firstName": "Nombre",
    "lastName": "Apellido",
    "active": true,
    "roles": ["ROLE_USUARIO"]
  },
  {
    "id": 2,
    "username": "vendedor1",
    "email": "vendedor1@example.com",
    "firstName": "Nombre Vendedor",
    "lastName": "Apellido Vendedor",
    "active": true,
    "roles": ["ROLE_VENDEDOR"]
  }
]
```

### Obtener Usuario por ID

**Endpoint:** `GET /api/admin/usuarios/{id}`

**Descripción:** Obtiene un usuario por su ID (solo para administradores).

**Parámetros de ruta:**
- id: ID del usuario

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Respuesta exitosa:**
```json
{
  "id": 1,
  "username": "usuario1",
  "email": "usuario1@example.com",
  "firstName": "Nombre",
  "lastName": "Apellido",
  "active": true,
  "roles": ["ROLE_USUARIO"]
}
```

### Activar/Desactivar Usuario

**Endpoint:** `PATCH /api/admin/usuarios/{id}/active`

**Descripción:** Activa o desactiva un usuario (solo para administradores).

**Parámetros de ruta:**
- id: ID del usuario

**Parámetros de consulta:**
- active: Estado de activación (true/false)

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Respuesta exitosa:**
```json
{
  "message": "Estado de usuario actualizado correctamente a: activo"
}
```

### Crear Vendedor

**Endpoint:** `POST /api/admin/usuarios/vendedores`

**Descripción:** Crea un nuevo usuario con rol de vendedor (solo para administradores).

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Cuerpo de la solicitud:**
```json
{
  "username": "vendedor2",
  "email": "vendedor2@example.com",
  "password": "password123",
  "firstName": "Nombre Vendedor",
  "lastName": "Apellido Vendedor"
}
```

**Respuesta exitosa:**
```json
{
  "id": 3,
  "username": "vendedor2",
  "email": "vendedor2@example.com",
  "firstName": "Nombre Vendedor",
  "lastName": "Apellido Vendedor",
  "active": true,
  "roles": ["ROLE_VENDEDOR"]
}
```

### Listar Todos los Vendedores

**Endpoint:** `GET /api/admin/usuarios/vendedores`

**Descripción:** Obtiene una lista de todos los vendedores (solo para administradores).

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Respuesta exitosa:**
```json
[
  {
    "id": 2,
    "username": "vendedor1",
    "email": "vendedor1@example.com",
    "firstName": "Nombre Vendedor",
    "lastName": "Apellido Vendedor",
    "active": true,
    "roles": ["ROLE_VENDEDOR"]
  },
  {
    "id": 3,
    "username": "vendedor2",
    "email": "vendedor2@example.com",
    "firstName": "Nombre Vendedor",
    "lastName": "Apellido Vendedor",
    "active": true,
    "roles": ["ROLE_VENDEDOR"]
  }
]
```

## Productos

### Obtener Todos los Productos (Paginados)

**Endpoint:** `GET /api/productos`

**Descripción:** Obtiene una lista paginada de productos.

**Parámetros de consulta:**
- page: Número de página (por defecto: 0)
- size: Tamaño de página (por defecto: 10)
- sort: Campo para ordenar (por defecto: "id")
- direction: Dirección de ordenamiento ("asc" o "desc", por defecto: "asc")

**Respuesta exitosa:**
```json
{
  "content": [
    {
      "id": 1,
      "nombre": "Producto 1",
      "descripcion": "Descripción del producto 1",
      "precio": 19.99,
      "stock": 100,
      "estado": "ACTIVO",
      "fechaRegistro": "2023-01-01T12:00:00",
      "categoriaId": 1
    },
    // Más productos...
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "sorted": true,
      "unsorted": false,
      "empty": false
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalElements": 50,
  "totalPages": 5,
  "last": false,
  "size": 10,
  "number": 0,
  "sort": {
    "sorted": true,
    "unsorted": false,
    "empty": false
  },
  "numberOfElements": 10,
  "first": true,
  "empty": false
}
```

### Obtener Todos los Productos (Sin Paginación)

**Endpoint:** `GET /api/productos/all`

**Descripción:** Obtiene una lista completa de productos sin paginación.

**Respuesta exitosa:**
```json
[
  {
    "id": 1,
    "nombre": "Producto 1",
    "descripcion": "Descripción del producto 1",
    "precio": 19.99,
    "stock": 100,
    "estado": "ACTIVO",
    "fechaRegistro": "2023-01-01T12:00:00",
    "categoriaId": 1
  },
  // Más productos...
]
```

### Obtener Productos por Categoría (Paginados)

**Endpoint:** `GET /api/productos/categoria/{categoriaId}`

**Descripción:** Obtiene una lista paginada de productos por categoría.

**Parámetros de ruta:**
- categoriaId: ID de la categoría

**Parámetros de consulta:**
- page: Número de página (por defecto: 0)
- size: Tamaño de página (por defecto: 10)
- sort: Campo para ordenar (por defecto: "id")
- direction: Dirección de ordenamiento ("asc" o "desc", por defecto: "asc")

**Respuesta exitosa:**
```json
{
  "content": [
    {
      "id": 1,
      "nombre": "Producto 1",
      "descripcion": "Descripción del producto 1",
      "precio": 19.99,
      "stock": 100,
      "estado": "ACTIVO",
      "fechaRegistro": "2023-01-01T12:00:00",
      "categoriaId": 1
    },
    // Más productos...
  ],
  "pageable": {
    // Información de paginación...
  },
  "totalElements": 20,
  "totalPages": 2,
  // Más información de paginación...
}
```

### Obtener Productos por Categoría (Sin Paginación)

**Endpoint:** `GET /api/productos/categoria/{categoriaId}/all`

**Descripción:** Obtiene una lista completa de productos por categoría sin paginación.

**Parámetros de ruta:**
- categoriaId: ID de la categoría

**Respuesta exitosa:**
```json
[
  {
    "id": 1,
    "nombre": "Producto 1",
    "descripcion": "Descripción del producto 1",
    "precio": 19.99,
    "stock": 100,
    "estado": "ACTIVO",
    "fechaRegistro": "2023-01-01T12:00:00",
    "categoriaId": 1
  },
  // Más productos...
]
```

### Obtener Productos por Tienda (Paginados)

**Endpoint:** `GET /api/productos/tienda/{tiendaId}`

**Descripción:** Obtiene una lista paginada de productos por tienda.

**Parámetros de ruta:**
- tiendaId: ID de la tienda

**Parámetros de consulta:**
- page: Número de página (por defecto: 0)
- size: Tamaño de página (por defecto: 10)
- sort: Campo para ordenar (por defecto: "id")
- direction: Dirección de ordenamiento ("asc" o "desc", por defecto: "asc")

**Respuesta exitosa:**
```json
{
  "content": [
    {
      "id": 1,
      "nombre": "Producto 1",
      "descripcion": "Descripción del producto 1",
      "precio": 19.99,
      "stock": 100,
      "estado": "ACTIVO",
      "fechaRegistro": "2023-01-01T12:00:00",
      "categoriaId": 1
    },
    // Más productos...
  ],
  "pageable": {
    // Información de paginación...
  },
  "totalElements": 30,
  "totalPages": 3,
  // Más información de paginación...
}
```

### Obtener Productos por Tienda (Sin Paginación)

**Endpoint:** `GET /api/productos/tienda/{tiendaId}/all`

**Descripción:** Obtiene una lista completa de productos por tienda sin paginación.

**Parámetros de ruta:**
- tiendaId: ID de la tienda

**Respuesta exitosa:**
```json
[
  {
    "id": 1,
    "nombre": "Producto 1",
    "descripcion": "Descripción del producto 1",
    "precio": 19.99,
    "stock": 100,
    "estado": "ACTIVO",
    "fechaRegistro": "2023-01-01T12:00:00",
    "categoriaId": 1
  },
  // Más productos...
]
```

### Crear Producto

**Endpoint:** `POST /api/productos`

**Descripción:** Crea un nuevo producto.

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Cuerpo de la solicitud:**
```json
{
  "nombre": "Nuevo Producto",
  "descripcion": "Descripción del nuevo producto",
  "precio": 29.99,
  "stock": 50,
  "estado": "ACTIVO",
  "categoriaId": 1
}
```

**Respuesta exitosa:**
```json
{
  "id": 51,
  "nombre": "Nuevo Producto",
  "descripcion": "Descripción del nuevo producto",
  "precio": 29.99,
  "stock": 50,
  "estado": "ACTIVO",
  "fechaRegistro": "2023-06-15T14:30:00",
  "categoriaId": 1
}
```

### Actualizar Producto

**Endpoint:** `PUT /api/productos/{id}`

**Descripción:** Actualiza un producto existente.

**Parámetros de ruta:**
- id: ID del producto a actualizar

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Cuerpo de la solicitud:**
```json
{
  "nombre": "Producto Actualizado",
  "descripcion": "Descripción actualizada",
  "precio": 39.99,
  "stock": 75,
  "estado": "ACTIVO",
  "categoriaId": 2
}
```

**Respuesta exitosa:**
```json
{
  "id": 1,
  "nombre": "Producto Actualizado",
  "descripcion": "Descripción actualizada",
  "precio": 39.99,
  "stock": 75,
  "estado": "ACTIVO",
  "fechaRegistro": "2023-01-01T12:00:00",
  "categoriaId": 2
}
```

### Eliminar Producto

**Endpoint:** `DELETE /api/productos/{id}`

**Descripción:** Elimina un producto.

**Parámetros de ruta:**
- id: ID del producto a eliminar

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Respuesta exitosa:**
- Código de estado 204 (No Content)

## Categorías

### Obtener Todas las Categorías

**Endpoint:** `GET /api/categorias`

**Descripción:** Obtiene una lista de todas las categorías.

**Respuesta exitosa:**
```json
[
  {
    "id": 1,
    "nombre": "Electrónicos",
    "descripcion": "Productos electrónicos",
    "tiendaId": 1
  },
  {
    "id": 2,
    "nombre": "Ropa",
    "descripcion": "Ropa y accesorios",
    "tiendaId": 2
  },
  // Más categorías...
]
```

### Obtener Categoría por ID

**Endpoint:** `GET /api/categorias/{id}`

**Descripción:** Obtiene una categoría por su ID.

**Parámetros de ruta:**
- id: ID de la categoría

**Respuesta exitosa:**
```json
{
  "id": 1,
  "nombre": "Electrónicos",
  "descripcion": "Productos electrónicos",
  "tiendaId": 1
}
```

### Obtener Categorías por Tienda

**Endpoint:** `GET /api/categorias/tienda/{tiendaId}`

**Descripción:** Obtiene una lista de categorías por tienda.

**Parámetros de ruta:**
- tiendaId: ID de la tienda

**Respuesta exitosa:**
```json
[
  {
    "id": 1,
    "nombre": "Electrónicos",
    "descripcion": "Productos electrónicos",
    "tiendaId": 1
  },
  {
    "id": 3,
    "nombre": "Computadoras",
    "descripcion": "Laptops y desktops",
    "tiendaId": 1
  },
  // Más categorías...
]
```

### Crear Categoría

**Endpoint:** `POST /api/categorias`

**Descripción:** Crea una nueva categoría.

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Cuerpo de la solicitud:**
```json
{
  "nombre": "Nueva Categoría",
  "descripcion": "Descripción de la nueva categoría",
  "tiendaId": 1
}
```

**Respuesta exitosa:**
```json
{
  "id": 10,
  "nombre": "Nueva Categoría",
  "descripcion": "Descripción de la nueva categoría",
  "tiendaId": 1
}
```

### Actualizar Categoría

**Endpoint:** `PUT /api/categorias/{id}`

**Descripción:** Actualiza una categoría existente.

**Parámetros de ruta:**
- id: ID de la categoría a actualizar

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Cuerpo de la solicitud:**
```json
{
  "nombre": "Categoría Actualizada",
  "descripcion": "Descripción actualizada",
  "tiendaId": 2
}
```

**Respuesta exitosa:**
```json
{
  "id": 1,
  "nombre": "Categoría Actualizada",
  "descripcion": "Descripción actualizada",
  "tiendaId": 2
}
```

### Eliminar Categoría

**Endpoint:** `DELETE /api/categorias/{id}`

**Descripción:** Elimina una categoría.

**Parámetros de ruta:**
- id: ID de la categoría a eliminar

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Respuesta exitosa:**
- Código de estado 204 (No Content)

## Tiendas

### Obtener Todas las Tiendas

**Endpoint:** `GET /api/tiendas`

**Descripción:** Obtiene una lista de todas las tiendas.

**Respuesta exitosa:**
```json
[
  {
    "id": 1,
    "nombre": "Tienda Electrónica",
    "descripcion": "Tienda de productos electrónicos",
    "direccion": "Calle Principal 123",
    "telefono": "123-456-7890",
    "email": "tienda1@example.com",
    "usuarioId": 2
  },
  // Más tiendas...
]
```

### Obtener Tienda por ID

**Endpoint:** `GET /api/tiendas/{id}`

**Descripción:** Obtiene una tienda por su ID.

**Parámetros de ruta:**
- id: ID de la tienda

**Respuesta exitosa:**
```json
{
  "id": 1,
  "nombre": "Tienda Electrónica",
  "descripcion": "Tienda de productos electrónicos",
  "direccion": "Calle Principal 123",
  "telefono": "123-456-7890",
  "email": "tienda1@example.com",
  "usuarioId": 2
}
```

### Crear Tienda

**Endpoint:** `POST /api/tiendas`

**Descripción:** Crea una nueva tienda.

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Cuerpo de la solicitud:**
```json
{
  "codigoTienda": "TIENDA001",  // IMPORTANTE: Este campo es obligatorio y debe ser único
  "nombre": "Nueva Tienda",
  "descripcion": "Descripción de la nueva tienda",
  "direccion": "Avenida Central 456",
  "telefono": "987-654-3210",
  "email": "nueva_tienda@example.com"
}
```

**Respuesta exitosa:**
```json
{
  "id": 5,
  "nombre": "Nueva Tienda",
  "descripcion": "Descripción de la nueva tienda",
  "direccion": "Avenida Central 456",
  "telefono": "987-654-3210",
  "email": "nueva_tienda@example.com",
  "usuarioId": 3
}
```

### Actualizar Tienda

**Endpoint:** `PUT /api/tiendas/{id}`

**Descripción:** Actualiza una tienda existente.

**Parámetros de ruta:**
- id: ID de la tienda a actualizar

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Cuerpo de la solicitud:**
```json
{
  "nombre": "Tienda Actualizada",
  "descripcion": "Descripción actualizada",
  "direccion": "Nueva Dirección 789",
  "telefono": "555-123-4567",
  "email": "tienda_actualizada@example.com"
}
```

**Respuesta exitosa:**
```json
{
  "id": 1,
  "nombre": "Tienda Actualizada",
  "descripcion": "Descripción actualizada",
  "direccion": "Nueva Dirección 789",
  "telefono": "555-123-4567",
  "email": "tienda_actualizada@example.com",
  "usuarioId": 2
}
```

### Eliminar Tienda

**Endpoint:** `DELETE /api/tiendas/{id}`

**Descripción:** Elimina una tienda.

**Parámetros de ruta:**
- id: ID de la tienda a eliminar

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Respuesta exitosa:**
- Código de estado 204 (No Content)

## Carritos

### Obtener Carrito del Usuario Autenticado

**Endpoint:** `GET /api/carrito`

**Descripción:** Obtiene el carrito del usuario autenticado.

**Encabezados requeridos:**
- Authorization: Bearer {token}

### Obtener Carrito por ID de Usuario (para compatibilidad)

**Endpoint:** `GET /api/carrito/{usuarioId}`

**Descripción:** Obtiene el carrito de un usuario específico.

**Parámetros de ruta:**
- usuarioId: ID del usuario

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Respuesta exitosa:**
```json
{
  "id": 1,
  "usuarioId": 1,
  "items": [
    {
      "id": 1,
      "productoId": 1,
      "nombreProducto": "Producto 1",
      "cantidad": 2,
      "precioUnitario": 19.99
    },
    {
      "id": 2,
      "productoId": 3,
      "nombreProducto": "Producto 3",
      "cantidad": 1,
      "precioUnitario": 29.99
    }
  ]
}
```

### Agregar Producto al Carrito del Usuario Autenticado

**Endpoint:** `POST /api/carrito/agregar`

**Descripción:** Agrega un producto al carrito del usuario autenticado.

**Encabezados requeridos:**
- Authorization: Bearer {token}

### Agregar Producto al Carrito por ID de Usuario (para compatibilidad)

**Endpoint:** `POST /api/carrito/{usuarioId}/agregar`

**Descripción:** Agrega un producto al carrito de un usuario específico.

**Parámetros de ruta:**
- usuarioId: ID del usuario

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Cuerpo de la solicitud:**
```json
{
  "productoId": 5,
  "cantidad": 3
}
```

**Respuesta exitosa:**
- Código de estado 200 (OK)

### Quitar Producto del Carrito del Usuario Autenticado

**Endpoint:** `DELETE /api/carrito/quitar/{productoId}`

**Descripción:** Elimina un producto del carrito del usuario autenticado.

**Parámetros de ruta:**
- productoId: ID del producto a eliminar

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Respuesta exitosa:**
- Código de estado 200 (OK)

### Quitar Producto del Carrito por ID de Usuario (para compatibilidad)

**Endpoint:** `DELETE /api/carrito/{usuarioId}/quitar/{productoId}`

**Descripción:** Elimina un producto del carrito de un usuario específico.

**Parámetros de ruta:**
- usuarioId: ID del usuario
- productoId: ID del producto a eliminar

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Respuesta exitosa:**
- Código de estado 200 (OK)

### Vaciar Carrito del Usuario Autenticado

**Endpoint:** `DELETE /api/carrito/vaciar`

**Descripción:** Vacía el carrito del usuario autenticado.

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Respuesta exitosa:**
- Código de estado 204 (No Content)

### Vaciar Carrito por ID de Usuario (para compatibilidad)

**Endpoint:** `DELETE /api/carrito/{usuarioId}/vaciar`

**Descripción:** Vacía el carrito de un usuario específico.

**Parámetros de ruta:**
- usuarioId: ID del usuario

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Respuesta exitosa:**
- Código de estado 204 (No Content)

## Pedidos

### Procesar Pedido del Usuario Autenticado

**Endpoint:** `POST /api/pedidos`

**Descripción:** Procesa un pedido a partir del carrito del usuario autenticado.

**Encabezados requeridos:**
- Authorization: Bearer {token}

### Procesar Pedido por ID de Usuario (para compatibilidad)

**Endpoint:** `POST /api/pedidos/{usuarioId}`

**Descripción:** Procesa un pedido a partir del carrito de un usuario específico.

**Parámetros de ruta:**
- usuarioId: ID del usuario

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Respuesta exitosa:**
```json
{
  "id": 1,
  "fechaCreacion": "2023-06-15T15:30:00",
  "estado": "PENDIENTE",
  "total": 89.95,
  "usuarioId": 1,
  "items": [
    {
      "id": 1,
      "productoId": 1,
      "nombreProducto": "Producto 1",
      "cantidad": 2,
      "precioUnitario": 19.99
    },
    {
      "id": 2,
      "productoId": 3,
      "nombreProducto": "Producto 3",
      "cantidad": 1,
      "precioUnitario": 29.99
    },
    {
      "id": 3,
      "productoId": 5,
      "nombreProducto": "Producto 5",
      "cantidad": 1,
      "precioUnitario": 19.98
    }
  ]
}
```

### Obtener Pedido por ID

**Endpoint:** `GET /api/pedidos/{id}`

**Descripción:** Obtiene un pedido por su ID.

**Parámetros de ruta:**
- id: ID del pedido

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Respuesta exitosa:**
```json
{
  "id": 1,
  "fechaCreacion": "2023-06-15T15:30:00",
  "estado": "PENDIENTE",
  "total": 89.95,
  "usuarioId": 1,
  "items": [
    {
      "id": 1,
      "productoId": 1,
      "nombreProducto": "Producto 1",
      "cantidad": 2,
      "precioUnitario": 19.99
    },
    {
      "id": 2,
      "productoId": 3,
      "nombreProducto": "Producto 3",
      "cantidad": 1,
      "precioUnitario": 29.99
    },
    {
      "id": 3,
      "productoId": 5,
      "nombreProducto": "Producto 5",
      "cantidad": 1,
      "precioUnitario": 19.98
    }
  ]
}
```

### Listar Pedidos del Usuario Autenticado

**Endpoint:** `GET /api/pedidos`

**Descripción:** Obtiene una lista de pedidos del usuario autenticado.

**Encabezados requeridos:**
- Authorization: Bearer {token}

### Listar Pedidos por ID de Usuario (para compatibilidad)

**Endpoint:** `GET /api/pedidos/usuario/{usuarioId}`

**Descripción:** Obtiene una lista de pedidos de un usuario específico.

**Parámetros de ruta:**
- usuarioId: ID del usuario

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Respuesta exitosa:**
```json
[
  {
    "id": 1,
    "fechaCreacion": "2023-06-15T15:30:00",
    "estado": "PENDIENTE",
    "total": 89.95,
    "usuarioId": 1,
    "items": [
      // Items del pedido...
    ]
  },
  {
    "id": 3,
    "fechaCreacion": "2023-06-10T11:45:00",
    "estado": "ENTREGADO",
    "total": 59.97,
    "usuarioId": 1,
    "items": [
      // Items del pedido...
    ]
  }
  // Más pedidos...
]
```

### Actualizar Estado de Pedido

**Endpoint:** `PATCH /api/pedidos/{id}/estado`

**Descripción:** Actualiza el estado de un pedido.

**Parámetros de ruta:**
- id: ID del pedido

**Parámetros de consulta:**
- estado: Nuevo estado del pedido (PENDIENTE, PAGADO, EN_PREPARACION, ENVIADO, ENTREGADO, CANCELADO)

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Respuesta exitosa:**
```json
{
  "id": 1,
  "fechaCreacion": "2023-06-15T15:30:00",
  "estado": "PAGADO",
  "total": 89.95,
  "usuarioId": 1,
  "items": [
    // Items del pedido...
  ]
}
```

## Pagos

### Procesar Pago

**Endpoint:** `POST /api/pagos`

**Descripción:** Procesa un pago para un pedido.

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Cuerpo de la solicitud:**
```json
{
  "pedidoId": 1,
  "metodoPago": "TARJETA",
  "numeroTarjeta": "4111111111111111",
  "fechaExpiracion": "12/25",
  "cvv": "123"
}
```

**Respuesta exitosa:**
```json
{
  "id": 1,
  "fechaPago": "2023-06-15T16:00:00",
  "monto": 89.95,
  "estado": "PAGADO",
  "metodoPago": "TARJETA",
  "pedidoId": 1,
  "referenciaPago": "PAY-123456789"
}
```

### Obtener Pago por ID

**Endpoint:** `GET /api/pagos/{id}`

**Descripción:** Obtiene un pago por su ID.

**Parámetros de ruta:**
- id: ID del pago

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Respuesta exitosa:**
```json
{
  "id": 1,
  "fechaPago": "2023-06-15T16:00:00",
  "monto": 89.95,
  "estado": "PAGADO",
  "metodoPago": "TARJETA",
  "pedidoId": 1,
  "referenciaPago": "PAY-123456789"
}
```

### Listar Pagos por Usuario

**Endpoint:** `GET /api/pagos/usuario/{usuarioId}`

**Descripción:** Obtiene una lista de pagos de un usuario.

**Parámetros de ruta:**
- usuarioId: ID del usuario

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Respuesta exitosa:**
```json
[
  {
    "id": 1,
    "fechaPago": "2023-06-15T16:00:00",
    "monto": 89.95,
    "estado": "PAGADO",
    "metodoPago": "TARJETA",
    "pedidoId": 1,
    "referenciaPago": "PAY-123456789"
  },
  {
    "id": 2,
    "fechaPago": "2023-06-10T12:15:00",
    "monto": 59.97,
    "estado": "PAGADO",
    "metodoPago": "PAYPAL",
    "pedidoId": 3,
    "referenciaPago": "PAY-987654321"
  }
  // Más pagos...
]
```

## Facturas

### Generar Factura

**Endpoint:** `POST /api/facturas`

**Descripción:** Genera una factura para un pago.

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Cuerpo de la solicitud:**
```json
{
  "pagoId": 1
}
```

**Respuesta exitosa:**
```json
{
  "id": 1,
  "numero": "F-12345678",
  "fechaEmision": "2023-06-15T16:05:00",
  "montoTotal": 89.95,
  "pagoId": 1
}
```

### Obtener Factura por ID

**Endpoint:** `GET /api/facturas/{id}`

**Descripción:** Obtiene una factura por su ID.

**Parámetros de ruta:**
- id: ID de la factura

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Respuesta exitosa:**
```json
{
  "id": 1,
  "numero": "F-12345678",
  "fechaEmision": "2023-06-15T16:05:00",
  "montoTotal": 89.95,
  "pagoId": 1
}
```

### Listar Facturas por Usuario

**Endpoint:** `GET /api/facturas/usuario/{usuarioId}`

**Descripción:** Obtiene una lista de facturas de un usuario.

**Parámetros de ruta:**
- usuarioId: ID del usuario

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Respuesta exitosa:**
```json
[
  {
    "id": 1,
    "numero": "F-12345678",
    "fechaEmision": "2023-06-15T16:05:00",
    "montoTotal": 89.95,
    "pagoId": 1
  },
  {
    "id": 2,
    "numero": "F-87654321",
    "fechaEmision": "2023-06-10T12:20:00",
    "montoTotal": 59.97,
    "pagoId": 2
  }
  // Más facturas...
]
```

## Reseñas de Productos

### Crear Reseña de Producto

**Endpoint:** `POST /api/productos/{productoId}/resenias`

**Descripción:** Crea una reseña para un producto.

**Parámetros de ruta:**
- productoId: ID del producto

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Cuerpo de la solicitud:**
```json
{
  "calificacion": 4,
  "comentario": "Muy buen producto, lo recomiendo."
}
```

**Nota:** La aplicación utiliza dos modelos de usuario (`User` para autenticación y `Usuario` para la lógica de negocio). Ahora, cuando un usuario se registra o inicia sesión, se crea automáticamente un `Usuario` asociado al `User` autenticado, por lo que no es necesario proporcionar el `usuarioId` en las solicitudes.

**Respuesta exitosa:**
```json
{
  "id": 1,
  "productoId": 1,
  "usuarioId": 1,
  "calificacion": 4,
  "comentario": "Muy buen producto, lo recomiendo.",
  "fechaCreacion": "2023-06-16T10:00:00"
}
```

### Obtener Reseñas por Producto

**Endpoint:** `GET /api/productos/{productoId}/resenias`

**Descripción:** Obtiene una lista de reseñas de un producto.

**Parámetros de ruta:**
- productoId: ID del producto

**Respuesta exitosa:**
```json
[
  {
    "id": 1,
    "productoId": 1,
    "usuarioId": 1,
    "nombreUsuario": "Usuario 1",
    "calificacion": 4,
    "comentario": "Muy buen producto, lo recomiendo.",
    "fechaCreacion": "2023-06-16T10:00:00"
  },
  {
    "id": 2,
    "productoId": 1,
    "usuarioId": 3,
    "nombreUsuario": "Usuario 3",
    "calificacion": 5,
    "comentario": "Excelente producto, superó mis expectativas.",
    "fechaCreacion": "2023-06-15T14:30:00"
  }
  // Más reseñas...
]
```

### Actualizar Reseña de Producto

**Endpoint:** `PUT /api/productos/{productoId}/resenias/{id}`

**Descripción:** Actualiza una reseña existente.

**Parámetros de ruta:**
- productoId: ID del producto
- id: ID de la reseña

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Cuerpo de la solicitud:**
```json
{
  "calificacion": 5,
  "comentario": "Actualizo mi reseña, el producto es excelente."
}
```

**Respuesta exitosa:**
```json
{
  "id": 1,
  "productoId": 1,
  "usuarioId": 1,
  "calificacion": 5,
  "comentario": "Actualizo mi reseña, el producto es excelente.",
  "fechaCreacion": "2023-06-16T10:00:00"
}
```

### Eliminar Reseña de Producto

**Endpoint:** `DELETE /api/productos/{productoId}/resenias/{id}`

**Descripción:** Elimina una reseña.

**Parámetros de ruta:**
- productoId: ID del producto
- id: ID de la reseña a eliminar

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Respuesta exitosa:**
- Código de estado 204 (No Content)

## Reseñas de Tiendas

### Listar Reseñas por Tienda

**Endpoint:** `GET /api/tiendas/{tiendaId}/resenias`

**Descripción:** Obtiene una lista de reseñas de una tienda.

**Parámetros de ruta:**
- tiendaId: ID de la tienda

**Respuesta exitosa:**
```json
[
  {
    "id": 1,
    "tiendaId": 1,
    "usuarioId": 1,
    "calificacion": 4,
    "comentario": "Buena tienda, envío rápido.",
    "fechaCreacion": "2023-06-16T10:00:00"
  },
  {
    "id": 2,
    "tiendaId": 1,
    "usuarioId": 3,
    "calificacion": 5,
    "comentario": "Excelente atención al cliente.",
    "fechaCreacion": "2023-06-15T14:30:00"
  }
  // Más reseñas...
]
```

### Crear Reseña de Tienda

**Endpoint:** `POST /api/tiendas/{tiendaId}/resenias`

**Descripción:** Crea una reseña para una tienda.

**Parámetros de ruta:**
- tiendaId: ID de la tienda

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Cuerpo de la solicitud:**
```json
{
  "calificacion": 4,
  "comentario": "Buena tienda, envío rápido."
}
```

**Nota:** La aplicación utiliza dos modelos de usuario (`User` para autenticación y `Usuario` para la lógica de negocio). Ahora, cuando un usuario se registra o inicia sesión, se crea automáticamente un `Usuario` asociado al `User` autenticado, por lo que no es necesario proporcionar el `usuarioId` en las solicitudes.

**Respuesta exitosa:**
```json
{
  "id": 1,
  "tiendaId": 1,
  "usuarioId": 1,
  "calificacion": 4,
  "comentario": "Buena tienda, envío rápido.",
  "fechaCreacion": "2023-06-16T10:00:00"
}
```

### Actualizar Reseña de Tienda

**Endpoint:** `PUT /api/tiendas/{tiendaId}/resenias/{id}`

**Descripción:** Actualiza una reseña de tienda existente.

**Parámetros de ruta:**
- tiendaId: ID de la tienda
- id: ID de la reseña

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Cuerpo de la solicitud:**
```json
{
  "calificacion": 5,
  "comentario": "Actualizo mi reseña, excelente atención al cliente."
}
```

**Respuesta exitosa:**
```json
{
  "id": 1,
  "tiendaId": 1,
  "usuarioId": 1,
  "calificacion": 5,
  "comentario": "Actualizo mi reseña, excelente atención al cliente.",
  "fechaCreacion": "2023-06-16T10:00:00"
}
```

### Eliminar Reseña de Tienda

**Endpoint:** `DELETE /api/tiendas/{tiendaId}/resenias/{id}`

**Descripción:** Elimina una reseña de tienda.

**Parámetros de ruta:**
- tiendaId: ID de la tienda
- id: ID de la reseña a eliminar

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Respuesta exitosa:**
- Código de estado 204 (No Content)

## Endpoints de Prueba

### Acceso Público

**Endpoint:** `GET /api/public/test`

**Descripción:** Endpoint de prueba para acceso público.

**Respuesta exitosa:**
```
Contenido Público
```

### Acceso para Usuarios

**Endpoint:** `GET /api/usuarios/test`

**Descripción:** Endpoint de prueba para usuarios autenticados.

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Respuesta exitosa:**
```
Contenido para Usuarios
```

### Acceso para Vendedores

**Endpoint:** `GET /api/vendedores/test`

**Descripción:** Endpoint de prueba para vendedores.

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Respuesta exitosa:**
```
Contenido para Vendedores
```

### Acceso para Administradores

**Endpoint:** `GET /api/admin/test`

**Descripción:** Endpoint de prueba para administradores.

**Encabezados requeridos:**
- Authorization: Bearer {token}

**Respuesta exitosa:**
```
Contenido para Administradores
```