# Documentación de Dockerización - API Ecommerce

## Descripción General
Este documento describe el proceso de dockerización de la API de Ecommerce desarrollada con Spring Boot.

## Requisitos Previos
- Docker
- Docker Compose
- JDK 21
- Maven

## Estructura de Dockerización

### Dockerfile
El proyecto utiliza un Dockerfile multi-etapa para optimizar el tamaño de la imagen final:

```dockerfile
# Etapa de construcción
FROM eclipse-temurin:21-jdk-alpine as build
WORKDIR /workspace/app

# Copiar archivos necesarios
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Dar permisos de ejecución al script mvnw
RUN chmod +x ./mvnw

# Construir el proyecto
RUN ./mvnw package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp
COPY --from=build /workspace/app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

### Docker Compose
El archivo `docker-compose.yml` configura el entorno de ejecución:

```yaml
version: '3.8'

services:
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://ep-little-haze-acl5496s-pooler.sa-east-1.aws.neon.tech/ecommerce_db?sslmode=require
      - SPRING_DATASOURCE_USERNAME=ecommerce_db_owner
      - SPRING_DATASOURCE_PASSWORD=npg_bXrIciMQR70t
      - SPRING_JPA_HIBERNATE_DDL_AUTO=update
      - SPRING_JPA_SHOW_SQL=true
      - SPRING_JPA_PROPERTIES_HIBERNATE_FORMAT_SQL=true
      - SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect
      - APP_JWTSECRET=QWERTYUIOPASDFGHJKLZXCVBNMQWERTYUIOPASDFGHJKLZXCVBNMQWERTYUIOPASDFGHJKLZXCVBNM
      - APP_JWTEXPIRATIONMS=86400000
      - SERVER_PORT=8080
      - SPRINGDOC_API_DOCS_ENABLED=true
      - SPRINGDOC_SWAGGER_UI_ENABLED=true
      - SPRINGDOC_SWAGGER_UI_PATH=/swagger-ui.html
      - SPRING_SESSION_STORE_TYPE=jdbc
      - SPRING_SESSION_JDBC_INITIALIZE_SCHEMA=always
      - SPRING_SESSION_TIMEOUT=3600
    restart: always
```

## Variables de Entorno
La aplicación utiliza las siguientes variables de entorno principales:

- **Base de Datos**:
  - `SPRING_DATASOURCE_URL`: URL de conexión a PostgreSQL
  - `SPRING_DATASOURCE_USERNAME`: Usuario de la base de datos
  - `SPRING_DATASOURCE_PASSWORD`: Contraseña de la base de datos

- **JWT**:
  - `APP_JWTSECRET`: Clave secreta para JWT
  - `APP_JWTEXPIRATIONMS`: Tiempo de expiración del token

- **Documentación**:
  - `SPRINGDOC_API_DOCS_ENABLED`: Habilita la documentación de la API
  - `SPRINGDOC_SWAGGER_UI_ENABLED`: Habilita la interfaz de Swagger
  - `SPRINGDOC_SWAGGER_UI_PATH`: Ruta de acceso a Swagger UI

## Comandos Útiles

### Construir y Ejecutar
```bash
# Construir la imagen
docker-compose build

# Iniciar los servicios
docker-compose up

# Ejecutar en modo detached
docker-compose up -d

# Detener los servicios
docker-compose down
```

### Ver Logs
```bash
# Ver logs de todos los servicios
docker-compose logs

# Ver logs de un servicio específico
docker-compose logs app

# Seguir los logs en tiempo real
docker-compose logs -f
```

## Acceso a la Aplicación
- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html

## Notas Importantes
1. La aplicación está configurada para reiniciarse automáticamente en caso de fallo (`restart: always`)
2. Se utiliza una base de datos PostgreSQL externa (Neon.tech)
3. La documentación de la API está disponible a través de Swagger UI
4. Las sesiones se almacenan en la base de datos (JDBC)