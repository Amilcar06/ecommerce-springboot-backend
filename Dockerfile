FROM eclipse-temurin:21-jdk-alpine as build
WORKDIR /workspace/app

# Copiar el archivo pom.xml y los archivos de configuración de Maven
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Dar permisos de ejecución al script mvnw
RUN chmod +x ./mvnw

# Construir el proyecto sin ejecutar tests
RUN ./mvnw package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp
COPY --from=build /workspace/app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]