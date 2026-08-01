# Paso 1: Compilar de forma nativa en la nube usando Gradle
FROM gradle:8-jdk17-alpine AS build
WORKDIR /app
COPY . .
RUN gradle clean bootJar --no-daemon

# Paso 2: Crear el contenedor de ejecución ultra ligero
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
