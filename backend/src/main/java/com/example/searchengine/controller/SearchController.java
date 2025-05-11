package com.example.searchengine.controller;

// Import necessary Spring annotations and classes
import com.example.searchengine.model.SearchHistory;
import com.example.searchengine.model.SearchRequest;
import com.example.searchengine.service.SearchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

// Allow CORS requests from the specified origin
@CrossOrigin(origins = "http://localhost:4200")
// Mark this class as a REST controller
@RestController
// Define the base URL for this controller
@RequestMapping("/search")
public class SearchController {

    // Inject the SearchHistoryService dependency
    @Autowired
    private SearchHistoryService searchHistoryService;

    // Endpoint to save a search query
    @PostMapping("/query")
    public ResponseEntity<SearchHistory> saveQuery(@RequestBody SearchRequest searchRequest) {
        // Save the query and retrieve the saved SearchHistory object
        SearchHistory savedHistory = searchHistoryService.saveQuery(searchRequest.getUserId(), searchRequest.getQuery());
        // Return the saved history in the response
        return ResponseEntity.ok(savedHistory);
    }

    // Endpoint to get the search history for a specific user
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<String>> getHistory(@PathVariable Long userId) {
        // Retrieve the user's search history from the service
        List<SearchHistory> userHistory = searchHistoryService.getUserHistory(userId);

        // Convert the list of SearchHistory objects to a list of query strings
        List<String> simplifiedHistory = userHistory.stream()
                .map(SearchHistory::getQuery)
                .collect(Collectors.toList());

        // Return the simplified history in the response
        return ResponseEntity.ok(simplifiedHistory);
    }

    // Test endpoint to check if the API is working
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("API is working!");
    }

    // Default endpoint to welcome users to the API
    @GetMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome to the Search Engine API!");
    }
}