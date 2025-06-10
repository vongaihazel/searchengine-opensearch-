# ---------- Stage 1: Build the Spring Boot app ----------
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy only the POM files first to cache dependencies
COPY pom.xml .
COPY backend/pom.xml ./backend/pom.xml
COPY frontend/pom.xml ./frontend/pom.xml

# Copy backend code only
COPY backend /app/backend
RUN mvn -f backend/pom.xml clean install -DskipTests

# ---------- Stage 2: Run the Spring Boot app ----------
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY --from=build /app/backend/target/backend-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
