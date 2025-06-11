# 🎯 Ejemplos Preconfigurados para Swagger

## ✅ **¡LISTO! Ejemplos ya configurados en Swagger**

He agregado ejemplos automáticos en los DTOs principales y algunos controladores. Cuando abras Swagger UI, verás ejemplos predefinidos en:

### 🔐 **Autenticación**

#### Registro de Usuario (`POST /api/auth/signup`)
```json
{
  "username": "usuario_test",
  "email": "usuario@test.com", 
  "password": "password123",
  "firstName": "Juan",
  "lastName": "Pérez",
  "roles": ["ROLE_USUARIO"]
}
```

#### Login (`POST /api/auth/login`)
```json
{
  "username": "usuario_test",
  "password": "password123"
}
```

### 🏪 **Tiendas** (Solo ADMIN)

#### Crear Tienda (`POST /api/tiendas`)
```json
{
  "codigoTienda": "TECH001",
  "nombre": "TechStore Premium",
  "descripcion": "Tienda especializada en tecnología y gaming",
  "categorias": ["Electrónicos", "Gaming", "Computadoras"]
}
```

### 📦 **Productos** (VENDEDOR + ADMIN)

#### Crear Producto (`POST /api/productos`)
```json
{
  "nombre": "Laptop Gaming ASUS ROG",
  "descripcion": "Laptop gaming con procesador Intel i7, 16GB RAM, RTX 3060",
  "precio": 1299.99,
  "stock": 25,
  "estado": "ACTIVO",
  "categoriaId": 1
}
```

### 📂 **Categorías** (Solo ADMIN)

#### Crear Categoría (`POST /api/categorias`)
```json
{
  "nombre": "Laptops Gaming",
  "tiendaId": 1
}
```

### 🛒 **Carrito** (Usuarios autenticados)

#### Agregar al Carrito (`POST /api/carritos/agregar`)
```json
{
  "productoId": 1,
  "cantidad": 2
}
```

### ⭐ **Reseñas** (Usuarios autenticados)

#### Crear Reseña de Producto (`POST /api/productos/{id}/resenias`)
```json
{
  "calificacion": 5,
  "comentario": "Excelente producto, muy buena calidad y llegó rápido"
}
```

## 🚀 **Cómo usar los ejemplos en Swagger:**

1. **Abre Swagger UI:** http://localhost:8081/swagger-ui.html

2. **Para endpoints que requieren autenticación:**
   - Primero registra un usuario con `/api/auth/signup`
   - Luego haz login con `/api/auth/login` 
   - Copia el token de la respuesta
   - Haz clic en "Authorize" 🔒 y pega el token

3. **Los ejemplos aparecen automáticamente:**
   - Al hacer clic en "Try it out" en cualquier endpoint
   - Los campos se llenan automáticamente con ejemplos
   - Solo haz clic en "Execute" para probar

## 📋 **Ejemplos rápidos para probar roles:**

### 👤 **Usuario Normal (ROLE_USUARIO)**
```json
{
  "username": "usuario_test",
  "email": "usuario@test.com",
  "password": "password123", 
  "firstName": "Juan",
  "lastName": "Usuario",
  "roles": ["ROLE_USUARIO"]
}
```

### 🛍️ **Vendedor (ROLE_VENDEDOR)**
```json
{
  "username": "vendedor_test",
  "email": "vendedor@test.com",
  "password": "password123",
  "firstName": "Carlos", 
  "lastName": "Vendedor",
  "roles": ["ROLE_VENDEDOR"]
}
```

### 👑 **Administrador (ROLE_ADMIN)**
```json
{
  "username": "admin_test",
  "email": "admin@test.com",
  "password": "password123",
  "firstName": "Ana",
  "lastName": "Admin", 
  "roles": ["ROLE_ADMIN"]
}
```
