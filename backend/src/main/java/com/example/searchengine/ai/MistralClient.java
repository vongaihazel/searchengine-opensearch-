package com.example.searchengine.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Component
public class MistralClient {

    private final WebClient webClient;

    public MistralClient(
            @Value("${mistral.api.key}") String apiKey,
            @Value("${mistral.api.url}") String apiUrl
    ) {
        this.webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
    }

    public Map<String, Object> generateCompletion(String prompt) {

        // Build request body as a Map (not raw JSON string)
        Map<String, Object> requestBody = Map.of(
                "model", "mistral-tiny",
                "messages", List.of(
                        Map.of("role", "user", "content", prompt)
                )
        );

        Map<String, Object> response = webClient.post()
                .uri("/chat/completions")
                .contentType(MediaType.APPLICATION_JSON) // Set Content-Type header correctly
                .bodyValue(requestBody)                   // Pass as Map, let WebClient handle JSON serialization
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        System.out.println("Response from Mistral: " + response);

        return response;
    }
}
