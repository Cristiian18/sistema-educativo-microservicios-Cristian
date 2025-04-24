FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
# Copiamos solo el pom.xml y el código fuente del proyecto
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/matriculas-servicio-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9082
ENTRYPOINT ["java", "-jar", "app.jar"]