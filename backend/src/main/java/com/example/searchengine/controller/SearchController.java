package com.example.searchengine.controller;

import com.example.searchengine.entity.SearchHistory;
import com.example.searchengine.dto.SearchRequest;
import com.example.searchengine.service.impl.SearchHistoryServiceImpl;
import com.example.searchengine.service.impl.OpenSearchServiceImpl;
import org.opensearch.action.search.SearchResponse;
import org.opensearch.search.SearchHit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for handling search-related endpoints, including executing search queries
 * using OpenSearch and managing user search history.
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchHistoryServiceImpl searchHistoryServiceImpl;

    @Autowired
    private OpenSearchServiceImpl openSearchService;

    /**
     * Executes a search query using OpenSearch and saves the query to the user's search history.
     *
     * @param searchRequest the search request containing user ID and query string
     * @return a combined response containing the saved search history and OpenSearch results;
     *         or a 500 error if OpenSearch fails
     */
    @PostMapping("/query")
    public ResponseEntity<?> searchAndSave(@RequestBody SearchRequest searchRequest) {
        SearchHistory savedHistory = searchHistoryServiceImpl.saveQuery(searchRequest.getUserId(), searchRequest.getQuery());

        try {
            SearchResponse searchResponse = openSearchService.search(searchRequest.getQuery());

            List<String> hits = List.of(searchResponse.getHits().getHits())
                    .stream()
                    .map(SearchHit::getSourceAsString)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new SearchResultResponse(savedHistory, hits));

        } catch (Exception e) {
            return ResponseEntity.status(500).body("OpenSearch error: " + e.getMessage());
        }
    }

    /**
     * Retrieves the search history for a specific user.
     *
     * @param userId the ID of the user
     * @return a list of search query strings
     */
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<String>> getHistory(@PathVariable Long userId) {
        List<SearchHistory> userHistory = searchHistoryServiceImpl.getUserHistory(userId);

        List<String> simplifiedHistory = userHistory.stream()
                .map(SearchHistory::getQuery)
                .collect(Collectors.toList());

        return ResponseEntity.ok(simplifiedHistory);
    }

    /**
     * A simple endpoint to verify that the API is running.
     *
     * @return a success message
     */
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("API is working!");
    }

    /**
     * A default welcome message for the base /search path.
     *
     * @return a welcome message
     */
    @GetMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome to the Search Engine API!");
    }

    /**
     * Inner class used to structure the combined response of saved search history and OpenSearch results.
     */
    public static class SearchResultResponse {
        public SearchHistory savedHistory;
        public List<String> openSearchResults;

        /**
         * Constructs a response object containing saved history and OpenSearch results.
         *
         * @param savedHistory the saved search history entry
         * @param openSearchResults the list of results from OpenSearch
         */
        public SearchResultResponse(SearchHistory savedHistory, List<String> openSearchResults) {
            this.savedHistory = savedHistory;
            this.openSearchResults = openSearchResults;
        }
    }
}
