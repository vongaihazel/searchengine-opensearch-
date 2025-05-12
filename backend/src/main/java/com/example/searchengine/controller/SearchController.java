package com.example.searchengine.controller;

import com.example.searchengine.model.SearchHistory;
import com.example.searchengine.model.SearchRequest;
import com.example.searchengine.service.SearchHistoryService;
import com.example.searchengine.service.OpenSearchService; // <-- NEW IMPORT
import org.opensearch.action.search.SearchResponse; // <-- NEW IMPORT
import org.opensearch.search.SearchHit; // <-- NEW IMPORT

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

    @Autowired // <-- NEW INJECTION
    private OpenSearchService openSearchService;

    // Endpoint to save a search query AND run OpenSearch query
    @PostMapping("/query")
    public ResponseEntity<?> searchAndSave(@RequestBody SearchRequest searchRequest) {
        // Save the query to search history (same as before)
        SearchHistory savedHistory = searchHistoryService.saveQuery(searchRequest.getUserId(), searchRequest.getQuery());

        try {
            // Run OpenSearch query (NEW)
            SearchResponse searchResponse = openSearchService.search(searchRequest.getQuery());

            // Extract hits as JSON strings (simpler for frontend)
            List<String> hits = List.of(searchResponse.getHits().getHits())
                    .stream()
                    .map(SearchHit::getSourceAsString)
                    .collect(Collectors.toList());

            // Return both history + hits in one response (optional but nice!)
            return ResponseEntity.ok(new SearchResultResponse(savedHistory, hits));

        } catch (Exception e) {
            return ResponseEntity.status(500).body("OpenSearch error: " + e.getMessage());
        }
    }

    // Endpoint to get the search history for a specific user
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<String>> getHistory(@PathVariable Long userId) {
        List<SearchHistory> userHistory = searchHistoryService.getUserHistory(userId);

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

    // NEW Inner static class for clean combined response
    public static class SearchResultResponse {
        public SearchHistory savedHistory;
        public List<String> openSearchResults;

        public SearchResultResponse(SearchHistory savedHistory, List<String> openSearchResults) {
            this.savedHistory = savedHistory;
            this.openSearchResults = openSearchResults;
        }
    }
}
