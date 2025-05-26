# ---------- Stage 1: Build ----------
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Set working directory
WORKDIR /app

# Copy the entire backend project
COPY . .

# Build the application (skip tests to speed it up)
RUN mvn clean install -DskipTests


# ---------- Stage 2: Runtime ----------
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy the JAR from the previous build stage
COPY --from=build /app/target/searchengine-0.0.1-SNAPSHOT.jar app.jar

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]