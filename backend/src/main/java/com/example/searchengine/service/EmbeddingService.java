package com.example.searchengine.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class EmbeddingService {

    private final String API_KEY = "YOUR_OPENAI_API_KEY";
    private final String EMBEDDING_URL = "https://api.openai.com/v1/embeddings";

    public List<Float> getEmbedding(String text) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> request = new HashMap<>();
        request.put("model", "text-embedding-3-large");
        request.put("input", text);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(EMBEDDING_URL, entity, Map.class);

        List<Float> embedding = (List<Float>) ((Map)((List)response.getBody().get("data")).get(0)).get("embedding");
        return embedding;
    }
}
