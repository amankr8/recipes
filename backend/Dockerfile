# Dockerfile
FROM openjdk:21-jdk-slim AS build
COPY . .
RUN ./mvnw clean package -DskipTests

FROM openjdk:21-jdk-slim
COPY --from=build target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080
