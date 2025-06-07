# 🛒 Ecommerce Spring Boot Backend

Backend REST API para una plataforma de comercio electrónico desarrollada con **Spring Boot**. El sistema incluye autenticación de usuarios, catálogo de productos, carrito de compras, procesamiento de pedidos y gestión de pagos.

## 🚀 Tecnologías utilizadas

- Java 21
- Spring Boot 3.4.3
- Spring Data JPA
- Spring Security + JWT
- Spring Validation
- Spring Cache + Redis
- PostgreSQL
- Maven
- Docker

## 📌 Funcionalidades principales

- Registro y login con JWT
- Roles de usuario (cliente, admin, vendedor)
- CRUD de productos, categorías y tiendas
- Carrito de compras
- Generación y seguimiento de pedidos
- Métodos de pago simulados
- Reseñas y calificaciones
- Cacheo con Redis para mejorar rendimiento

```bash
  ./mvnw spring-boot:run
```

## 🔧 Configuración inicial

1. Clona el repositorio:
   ```
    git clone https://github.com/tuusuario/ecommerce-springboot-backend.git
    cd ecommerce-springboot-backend
   ```
   
2. Configura el archivo application.properties con tus credenciales de base de datos PostgreSQL y Redis:

properties
  ```
    spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce
    spring.datasource.username=tu_usuario
    spring.datasource.password=tu_contraseña
    spring.jpa.hibernate.ddl-auto=update
    spring.redis.host=localhost
    spring.redis.port=6379
```

3. Ejecuta el proyecto:
```bash
    ./mvnw spring-boot:run
```
## 🐳 Ejecución con Docker

1. Construye la imagen Docker:
   ```bash
   docker build -t ecommerce-api .
   ```

2. Ejecuta el contenedor:
   ```bash
   docker run -p 8080:8080 ecommerce-api
   ```

3. Alternativamente, usa Docker Compose:
   ```bash
   docker-compose up
   ```

## 🚀 Despliegue en Render

1. Crea una cuenta en [Render](https://render.com/) si aún no tienes una.

2. Conecta tu repositorio de GitHub a Render.

3. Crea un nuevo servicio web y selecciona la opción "Deploy from Git Repository".

4. Selecciona tu repositorio y configura el servicio:
   - Tipo de servicio: Web Service
   - Entorno: Docker
   - Plan: Free (o el que prefieras)
   - Región: La más cercana a tus usuarios

5. Render detectará automáticamente el Dockerfile y desplegará tu aplicación.

6. Alternativamente, puedes usar el archivo `render.yaml` incluido en el repositorio para configurar el despliegue.
## 🧑‍💻 Equipo de desarrollo
Nombre  |  Rol          |	Tareas asignadas
- Dev 1	|  Backend Lead |	Seguridad, JWT, Auth

## 🧑‍💻 Equipo de desarrollo
Nombre  |  Rol          |	Tareas asignadas
- Dev 1	|  Backend Lead |	Seguridad, JWT, Auth
- Dev 2	|  Backend	CRUD| productos/categorías/tiendas
- Dev 3	|  Backend      |	Carrito de compras
- Dev 4	| Backend	      | Pedidos y pagos
- Dev 5	| Backend	      | Reseñas, validaciones y testing
- Dev 6	| Infra	        | Configuración, Redis, documentación, integración frontend
