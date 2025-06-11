# 🧪 Guía Completa para Probar la API con Swagger

## 🚀 Acceso a Swagger UI

**URL:** http://localhost:8081/swagger-ui.html

## 📋 Pasos para Probar la Protección por Roles

### 1. 🔍 **Verificar Endpoints Públicos (Sin Autenticación)**

Estos endpoints NO requieren token y deberían funcionar directamente:

#### ✅ Endpoints de Solo Lectura Públicos:
- `GET /api/tiendas` - Listar tiendas
- `GET /api/productos` - Listar productos (con paginación)
- `GET /api/productos/all` - Listar todos los productos
- `GET /api/categorias` - Listar categorías
- `GET /api/productos/{productoId}/resenias` - Ver reseñas de productos
- `GET /api/tiendas/{tiendaId}/resenias` - Ver reseñas de tiendas

#### ✅ Endpoints de Autenticación:
- `POST /api/auth/signup` - Registro de usuario
- `POST /api/auth/login` - Inicio de sesión
- `GET /api/auth/session-info` - Información de sesión

#### ✅ Endpoints de Prueba:
- `GET /api/public/test` - Endpoint público de prueba

---

### 2. 👤 **Crear Usuarios de Prueba**

#### A. Crear Usuario Normal (ROLE_USUARIO)
```json
POST /api/auth/signup
{
  "username": "usuario_test",
  "email": "usuario@test.com",
  "password": "password123",
  "firstName": "Usuario",
  "lastName": "Test",
  "role": ["ROLE_USUARIO"]
}
```

#### B. Crear Usuario Vendedor (ROLE_VENDEDOR)
```json
POST /api/auth/signup
{
  "username": "vendedor_test",
  "email": "vendedor@test.com",
  "password": "password123",
  "firstName": "Vendedor",
  "lastName": "Test",
  "role": ["ROLE_VENDEDOR"]
}
```

#### C. Crear Usuario Administrador (ROLE_ADMIN)
```json
POST /api/auth/signup
{
  "username": "admin_test",
  "email": "admin@test.com",
  "password": "password123",
  "firstName": "Admin",
  "lastName": "Test",
  "role": ["ROLE_ADMIN"]
}
```

---

### 3. 🔐 **Obtener Tokens de Autenticación**

Para cada usuario creado, obtén su token:

```json
POST /api/auth/login
{
  "username": "usuario_test",
  "password": "password123"
}
```

**Respuesta esperada:**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "type": "Bearer",
  "id": 1,
  "username": "usuario_test",
  "email": "usuario@test.com",
  "roles": ["ROLE_USUARIO"]
}
```

**⚠️ IMPORTANTE:** Copia el valor del campo `token` (sin incluir "Bearer")

---

### 4. 🔑 **Configurar Autenticación en Swagger**

1. En Swagger UI, busca el botón **"Authorize"** (🔒) en la parte superior
2. Haz clic en él
3. En el campo que aparece, pega SOLO el token (sin "Bearer ")
4. Haz clic en "Authorize"
5. Haz clic en "Close"

---

### 5. 🧪 **Pruebas por Rol**

#### 🟢 **Pruebas con ROLE_USUARIO**

**✅ Debería FUNCIONAR:**
- `GET /api/profile` - Ver perfil
- `PUT /api/profile` - Actualizar perfil
- `POST /api/profile/change-password` - Cambiar contraseña
- `GET /api/carritos` - Ver carrito
- `POST /api/carritos/agregar` - Agregar al carrito
- `POST /api/pedidos` - Crear pedido
- `POST /api/pagos` - Crear pago
- `POST /api/facturas/generar/{pagoId}` - Generar factura
- `POST /api/productos/{productoId}/resenias` - Crear reseña de producto
- `GET /api/usuarios/test` - Endpoint de prueba para usuarios

**❌ Debería FALLAR (403 Forbidden):**
- `POST /api/tiendas` - Crear tienda
- `POST /api/categorias` - Crear categoría
- `POST /api/productos` - Crear producto
- `GET /api/admin/usuarios` - Listar usuarios
- `GET /api/vendedores/test` - Endpoint de prueba para vendedores
- `GET /api/admin/test` - Endpoint de prueba para admin

#### 🟡 **Pruebas con ROLE_VENDEDOR**

**✅ Debería FUNCIONAR:**
- Todo lo que funciona para ROLE_USUARIO +
- `POST /api/productos` - Crear producto
- `PUT /api/productos/{id}` - Actualizar producto
- `DELETE /api/productos/{id}` - Eliminar producto
- `PATCH /api/pagos/{id}/estado` - Actualizar estado de pago
- `GET /api/vendedores/test` - Endpoint de prueba para vendedores

**❌ Debería FALLAR (403 Forbidden):**
- `POST /api/tiendas` - Crear tienda
- `POST /api/categorias` - Crear categoría
- `GET /api/admin/usuarios` - Listar usuarios
- `GET /api/pagos` - Listar todos los pagos
- `GET /api/admin/test` - Endpoint de prueba para admin

#### 🔴 **Pruebas con ROLE_ADMIN**

**✅ Debería FUNCIONAR:**
- TODO (acceso completo a todos los endpoints)
- `POST /api/tiendas` - Crear tienda
- `PUT /api/tiendas/{id}` - Actualizar tienda
- `DELETE /api/tiendas/{id}` - Eliminar tienda
- `POST /api/categorias` - Crear categoría
- `PUT /api/categorias/{id}` - Actualizar categoría
- `DELETE /api/categorias/{id}` - Eliminar categoría
- `GET /api/admin/usuarios` - Listar usuarios
- `POST /api/admin/usuarios/vendedores` - Crear vendedor
- `GET /api/pagos` - Listar todos los pagos
- `GET /api/admin/test` - Endpoint de prueba para admin

---

### 6. 🔍 **Verificar Respuestas de Error**

#### Sin Token (401 Unauthorized):
```json
{
  "timestamp": "2023-06-11T04:34:27.131Z",
  "status": 401,
  "error": "Unauthorized",
  "path": "/api/productos"
}
```

#### Token Inválido o Expirado (401 Unauthorized):
```json
{
  "timestamp": "2023-06-11T04:34:27.131Z",
  "status": 401,
  "error": "Unauthorized",
  "message": "Full authentication is required to access this resource"
}
```

#### Sin Permisos (403 Forbidden):
```json
{
  "timestamp": "2023-06-11T04:34:27.131Z",
  "status": 403,
  "error": "Forbidden",
  "message": "Access Denied"
}
```

---

### 7. 📊 **Casos de Prueba Específicos**

#### Caso 1: Usuario Normal intenta crear tienda
1. Autentícate con token de ROLE_USUARIO
2. Intenta `POST /api/tiendas`
3. **Resultado esperado:** 403 Forbidden

#### Caso 2: Vendedor intenta crear producto
1. Autentícate con token de ROLE_VENDEDOR
2. Intenta `POST /api/productos`
3. **Resultado esperado:** 200 OK (éxito)

#### Caso 3: Admin crea categoría
1. Autentícate con token de ROLE_ADMIN
2. Intenta `POST /api/categorias`
3. **Resultado esperado:** 200 OK (éxito)

#### Caso 4: Sin autenticación ve productos
1. NO uses token (botón "Logout" en Authorize)
2. Intenta `GET /api/productos`
3. **Resultado esperado:** 200 OK (éxito - público)

#### Caso 5: Sin autenticación intenta crear producto
1. NO uses token
2. Intenta `POST /api/productos`
3. **Resultado esperado:** 401 Unauthorized

---

### 8. 🎯 **Checklist de Verificación**

- [ ] Endpoints públicos funcionan sin token
- [ ] Endpoints protegidos fallan sin token (401)
- [ ] ROLE_USUARIO puede usar carrito/pedidos/pagos
- [ ] ROLE_USUARIO NO puede crear tiendas/categorías
- [ ] ROLE_VENDEDOR puede crear/editar productos
- [ ] ROLE_VENDEDOR NO puede crear tiendas
- [ ] ROLE_ADMIN puede hacer todo
- [ ] Tokens inválidos son rechazados
- [ ] Roles incorrectos reciben 403 Forbidden

---

## 🚨 **Solución de Problemas**

### Error: "Failed to fetch"
- Verifica que la aplicación esté ejecutándose en http://localhost:8081
- Revisa la consola del navegador para errores CORS

### Error: 401 Unauthorized constante
- Verifica que el token esté correctamente copiado (sin "Bearer ")
- Asegúrate de que el token no haya expirado (24 horas)
- Intenta hacer login nuevamente

### Error: 403 Forbidden inesperado
- Verifica que el usuario tenga el rol correcto
- Revisa que el endpoint esté correctamente configurado

---

## 🎉 **¡Tu API está completamente protegida!**

Si todas las pruebas pasan según lo esperado, tu API tiene una protección por roles perfectamente implementada.