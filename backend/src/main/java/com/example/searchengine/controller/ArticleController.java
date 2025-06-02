package com.example.searchengine.controller;

import com.example.searchengine.model.Article;
import com.example.searchengine.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * REST controller for handling API requests related to {@link Article} resources.
 * <p>
 * This controller provides endpoints for adding new articles and searching existing ones
 * using OpenSearch.
 * </p>
 */
@RestController
@RequestMapping("/api/articles")
@CrossOrigin
public class ArticleController {

    private final ArticleService service;

    /**
     * Constructor for injecting the {@link ArticleService}.
     *
     * @param service the service responsible for article operations
     */
    public ArticleController(ArticleService service) {
        this.service = service;
    }

    /**
     * Endpoint for saving a new article to OpenSearch.
     *
     * @param article the article to be saved
     * @throws IOException if an I/O error occurs while saving the article
     */
    @PostMapping
    public void addArticle(@RequestBody Article article) throws IOException {
        service.saveArticle(article);
    }

    /**
     * Endpoint for searching articles using a query string.
     *
     * @param query the search query
     * @return a list of articles matching the query
     * @throws IOException if an I/O error occurs while performing the search
     */
    @GetMapping("/search")
    public List<Article> searchArticles(@RequestParam String query) throws IOException {
        return service.search(query);
    }
}
