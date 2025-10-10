# Etapa de compilación
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# Expone el puerto de la app
EXPOSE 8080

# Ejecuta Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
