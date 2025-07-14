package com.example.searchengine.controller;

import com.example.searchengine.dto.SearchQueryRequest;
import com.example.searchengine.dto.SearchQueryResponse;
import com.example.searchengine.dto.VectorSearchRequest;
import com.example.searchengine.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling API requests related to articles.
 * <p>
 * Provides endpoint to search articles with optional filters.
 * </p>
 */
@RestController
@RequestMapping("/api/articles")
@CrossOrigin
public class ArticleController {

    private final ArticleService articleService;

    /**
     * Constructor for injecting the ArticleService.
     *
     * @param articleService the service responsible for article operations
     */
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * Endpoint for searching articles with query and optional filters.
     *
     * @param request the search request containing query and filters
     * @return the search response with matching articles and total hits, or HTTP 500 if error occurs
     */
    @PostMapping("/search")
    public ResponseEntity<?> searchArticles(@RequestBody SearchQueryRequest request) {
        try {
            SearchQueryResponse response = articleService.searchArticles(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Optionally log the exception here
            return ResponseEntity.status(500).body("Search failed due to internal error.");
        }
    }

    @PostMapping("/vector-search")
    public ResponseEntity<?> vectorSearch(@RequestBody VectorSearchRequest request) {
        try {
            SearchQueryResponse response = articleService.searchByVector(request.getEmbedding(), request.getK());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();  // print full error to console/log
            return ResponseEntity.status(500).body("Vector search failed: " + e.getMessage());
        }
    }

}
