package com.example.searchengine.controller;

import com.example.searchengine.model.SearchHistory;
import com.example.searchengine.model.SearchRequest;
import com.example.searchengine.service.SearchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchHistoryService searchHistoryService;

    @PostMapping("/query")
    public ResponseEntity<SearchHistory> saveQuery(@RequestBody SearchRequest searchRequest) {
        SearchHistory savedHistory = searchHistoryService.saveQuery(searchRequest.getUserId(), searchRequest.getQuery());
        return ResponseEntity.ok(savedHistory);
    }

    // Endpoint: GET /search/history/{userId}
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<String>> getHistory(@PathVariable Long userId) {
        // Make sure the service returns List<SearchHistory> (NOT List<String>)
        List<SearchHistory> userHistory = searchHistoryService.getUserHistory(userId);

        // Convert SearchHistory list to list of query strings
        List<String> simplifiedHistory = userHistory.stream()
                .map(SearchHistory::getQuery)
                .collect(Collectors.toList());

        return ResponseEntity.ok(simplifiedHistory);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("API is working!");
    }

    @GetMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome to the Search Engine API!");
    }
}
