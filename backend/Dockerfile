# Use the OpenJDK 11 slim image as the base image
FROM openjdk:11-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the current directory contents into the container
COPY . .

# Check if the JAR file exists and handle accordingly
RUN if [ -f target/backend.jar ]; then \
        echo "JAR file found, proceeding with copy"; \
        cp target/backend.jar app.jar; \
    else \
        echo "JAR file not found, skipping copy"; \
    fi

# Set the entry point for the container to run the Java application
ENTRYPOINT ["java", "-jar", "app.jar"]