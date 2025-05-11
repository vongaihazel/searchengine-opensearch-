package com.example.searchengine.config;

// Import necessary Spring annotations and interfaces
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Mark this class as a configuration class for Spring
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Override the addCorsMappings method to configure CORS settings
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow all endpoints to receive CORS requests
        registry.addMapping("/**")
                // Specify the origin that is allowed to make requests (e.g., Angular app)
                .allowedOrigins("http://localhost:4200")
                // Specify the HTTP methods that are allowed
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // Allow all headers to be sent in the requests
                .allowedHeaders("*")
                // Allow credentials like cookies or authorization headers to be included
                .allowCredentials(true);
    }
}