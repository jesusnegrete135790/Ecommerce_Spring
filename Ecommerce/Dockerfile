# 1. Imagen base con Java 21 muy ligera (Alpine)
FROM eclipse-temurin:21-jdk-alpine

# 2. Directorio de trabajo dentro del contenedor
WORKDIR /app

# 3. Copiamos el .jar que acabas de generar a la carpeta /app del contenedor
COPY target/Ecommerce-0.0.1-SNAPSHOT.jar app.jar

# 4. Exponemos el puerto en el que corre Spring Boot
EXPOSE 8080

# 5. El comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]