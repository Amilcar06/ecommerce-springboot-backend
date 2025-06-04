# 🛒 Ecommerce Spring Boot Backend

Backend REST API para una plataforma de comercio electrónico desarrollada con **Spring Boot**. El sistema incluye autenticación de usuarios, catálogo de productos, carrito de compras, procesamiento de pedidos y gestión de pagos.

## 🚀 Tecnologías utilizadas

- Java 21
- Spring Boot 3.4.3
- Spring Data JPA
- Spring Security + JWT
- Spring Validation
- Spring Cache
- PostgreSQL (Neon Tech)
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

## 🔧 Configuración inicial

1. Clona el repositorio:
   ```
    git clone https://github.com/tuusuario/ecommerce-springboot-backend.git
    cd ecommerce-springboot-backend
   ```
   
2. Configura el archivo application.properties con tus credenciales de base de datos PostgreSQL:

   ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce
    spring.datasource.username=tu_usuario
    spring.datasource.password=tu_contraseña
    spring.jpa.hibernate.ddl-auto=update
   ```

3. Ejecuta el proyecto:
   ```bash
    ./mvnw spring-boot:run
   ```

## 🐳 Despliegue con Docker

### Desarrollo local con Docker

Para ejecutar la aplicación localmente con Docker:

```bash
# Construir la imagen
docker build -t ecommerce-backend .

# Ejecutar el contenedor
docker run -p 8080:8080 ecommerce-backend
```

### Despliegue en Render

Este proyecto está configurado para ser desplegado en Render utilizando Docker.

#### Pasos para el despliegue:

1. Crea una cuenta en [Render](https://render.com/) si aún no tienes una.

2. Conecta tu repositorio de GitHub a Render.

3. Crea un nuevo servicio web y selecciona el repositorio.

4. Selecciona "Docker" como entorno.

5. Configura el servicio:
   - **Nombre**: ecommerce-springboot-backend (o el que prefieras)
   - **Plan**: Free
   - **Región**: La más cercana a tus usuarios
   - **Rama**: main (o la que uses para producción)
   - **Ruta del Dockerfile**: ./Dockerfile
   - **Ruta de verificación de salud**: /api/public/test

6. Haz clic en "Crear servicio web".

Render utilizará el archivo `render.yaml` para configurar automáticamente el servicio.
## 🧑‍💻 Equipo de desarrollo
Nombre  |  Rol          |	Tareas asignadas
- Dev 1	|  Backend Lead |	Seguridad, JWT, Auth
- Dev 2	|  Backend	CRUD| productos/categorías/tiendas
- Dev 3	|  Backend      |	Carrito de compras
- Dev 4	| Backend	      | Pedidos y pagos
- Dev 5	| Backend	      | Reseñas, validaciones y testing
- Dev 6	| Infra	        | Configuración, Redis, documentación, integración frontend


### Autentificacion
Mas informacion en el documento de [Autentificacion](md/Autentificacion.md)
