# Stage 1: builder
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy only pom files to leverage Docker cache for dependencies
COPY pom.xml ./
COPY backend/pom.xml ./backend/pom.xml
COPY frontend/pom.xml ./frontend/pom.xml

RUN mvn dependency:go-offline

# Copy the full project source code
COPY . .

# Build the whole project without running tests
RUN mvn clean install -DskipTests

# Stage 2: runtime
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copy the built Spring Boot fat jar from the build stage
COPY --from=build /app/backend/target/backend-1.0.0.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
