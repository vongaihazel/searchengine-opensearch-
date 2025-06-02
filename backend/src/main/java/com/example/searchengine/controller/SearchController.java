package com.example.searchengine.controller;

import com.example.searchengine.dto.SearchResultResponse;
import com.example.searchengine.entity.SearchHistory;
import com.example.searchengine.dto.SearchRequest;
import com.example.searchengine.service.impl.SearchHistoryServiceImpl;
import com.example.searchengine.service.impl.OpenSearchServiceImpl;
import com.example.searchengine.model.Article;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.search.Hit;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchHistoryServiceImpl searchHistoryServiceImpl;
    private final OpenSearchServiceImpl openSearchService;

    public SearchController(SearchHistoryServiceImpl searchHistoryServiceImpl,
                            OpenSearchServiceImpl openSearchService) {
        this.searchHistoryServiceImpl = searchHistoryServiceImpl;
        this.openSearchService = openSearchService;
    }

    @PostMapping("/query")
    public ResponseEntity<?> searchAndSave(@RequestBody SearchRequest searchRequest) {
        SearchHistory savedHistory = searchHistoryServiceImpl.saveQuery(searchRequest.getUserId(), searchRequest.getQuery());

        try {
            System.out.println("Running OpenSearch query for: " + searchRequest.getQuery());

            SearchResponse<Article> searchResponse = openSearchService.search(searchRequest.getQuery());

            List<Article> articles = searchResponse.hits().hits().stream()
                    .map(Hit::source)
                    .filter(a -> a != null)
                    .collect(Collectors.toList());

            // 🟢 Return both history + articles
            return ResponseEntity.ok(new SearchResultResponse(savedHistory, articles));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("OpenSearch error: " + e.getMessage());
        }
    }

    @PostMapping("/api/articles")
    public ResponseEntity<String> addArticle(@RequestBody Article article) {
        try {
            openSearchService.indexArticle(article);
            return ResponseEntity.ok("Article indexed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error indexing article: " + e.getMessage());
        }
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<String>> getHistory(@PathVariable("userId") Long userId) {
        List<SearchHistory> userHistory = searchHistoryServiceImpl.getUserHistory(userId);
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
