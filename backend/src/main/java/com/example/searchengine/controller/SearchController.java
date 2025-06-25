package com.example.searchengine.controller;

import com.example.searchengine.dto.SearchResultResponse;
import com.example.searchengine.entity.SearchHistory;
import com.example.searchengine.dto.SearchQueryRequest;
import com.example.searchengine.service.impl.SearchHistoryServiceImpl;
import com.example.searchengine.service.impl.OpenSearchServiceImpl;
import com.example.searchengine.model.Article;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.search.Hit;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller that provides endpoints for performing searches,
 * saving search history, retrieving search history, and indexing articles.
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchHistoryServiceImpl searchHistoryServiceImpl;
    private final OpenSearchServiceImpl openSearchService;

    /**
     * Constructor for injecting required services.
     *
     * @param searchHistoryServiceImpl the service for handling search history operations
     * @param openSearchService        the service for interacting with OpenSearch
     */
    public SearchController(SearchHistoryServiceImpl searchHistoryServiceImpl,
                            OpenSearchServiceImpl openSearchService) {
        this.searchHistoryServiceImpl = searchHistoryServiceImpl;
        this.openSearchService = openSearchService;
    }

    /**
     * Handles POST requests to perform a search and save the query to search history.
     *
     * @param searchQueryRequest the request payload containing user ID and query string
     * @return a response containing saved search history and matched articles,
     * or an error message in case of failure
     */
    @PostMapping("/query")
    public ResponseEntity<?> searchAndSave(@RequestBody SearchQueryRequest searchQueryRequest) {
        SearchHistory savedHistory = searchHistoryServiceImpl.saveQuery(searchQueryRequest.getUserId(), searchQueryRequest.getQuery());

        try {
            System.out.println("Running OpenSearch query for: " + searchQueryRequest.getQuery());

            SearchResponse<Article> searchResponse = openSearchService.search(searchQueryRequest.getQuery());

            List<Article> articles = searchResponse.hits().hits().stream()
                    .map(Hit::source)
                    .filter(a -> a != null)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new SearchResultResponse(savedHistory, articles));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("OpenSearch error: " + e.getMessage());
        }
    }

    /**
     * Handles POST requests to index a new article into OpenSearch.
     *
     * @param article the article to be indexed
     * @return a response indicating success or failure
     */
    @PostMapping("/api/articles")
    public ResponseEntity<String> addArticle(@RequestBody Article article) {
        try {
            openSearchService.indexArticle(article);
            return ResponseEntity.ok("Article indexed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error indexing article: " + e.getMessage());
        }
    }

    /**
     * Retrieves the search history for a specific user.
     *
     * @param userId the ID of the user
     * @return a list of query strings previously searched by the user
     */
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<String>> getHistory(@PathVariable("userId") Long userId) {
        List<SearchHistory> userHistory = searchHistoryServiceImpl.getUserHistory(userId);
        List<String> simplifiedHistory = userHistory.stream()
                .map(SearchHistory::getQuery)
                .collect(Collectors.toList());
        return ResponseEntity.ok(simplifiedHistory);
    }

    /**
     * A simple endpoint to test if the API is running.
     *
     * @return a success message
     */
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("API is working!");
    }

    /**
     * Default root endpoint for the search API.
     *
     * @return a welcome message
     */
    @GetMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome to the Search Engine API!");
    }
}
