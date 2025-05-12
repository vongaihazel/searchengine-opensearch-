# Use the OpenJDK 11 slim image as the base image
FROM openjdk:11-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Copy only the necessary files to the container
COPY target/backend.jar app.jar

# Set the entry point for the container to run the Java application
ENTRYPOINT ["java", "-jar", "app.jar"]