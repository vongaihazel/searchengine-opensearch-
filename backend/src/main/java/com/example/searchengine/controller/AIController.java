package com.example.searchengine.controller;

import com.example.searchengine.ai.MistralClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    private final MistralClient mistralClient;

    @Autowired
    public AIController(MistralClient mistralClient) {
        this.mistralClient = mistralClient;
    }

    @PostMapping("/complete")
    public Map<String, Object> completePrompt(@RequestBody Map<String, String> body) {
        String prompt = body.get("prompt");
        return mistralClient.generateCompletion(prompt);
    }
}
