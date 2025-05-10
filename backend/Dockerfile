# backend/Dockerfile
FROM openjdk:11-jre-slim
WORKDIR /app

# Check if the JAR file exists and handle accordingly
COPY . .
RUN if [ -f target/backend.jar ]; then \
        echo "JAR file found, proceeding with copy"; \
        cp target/backend.jar app.jar; \
    else \
        echo "JAR file not found, skipping copy"; \
    fi

ENTRYPOINT ["java", "-jar", "app.jar"]