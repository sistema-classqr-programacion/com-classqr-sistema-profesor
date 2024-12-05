# Usa una imagen base de JDK para ejecutar el JAR
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR generado al contenedor
COPY target/profesor-0.0.1-SNAPSHOT.jar profesor-ms.jar

# Expone el puerto 8001
EXPOSE 8002

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "profesor-ms.jar"]