package com.example.searchengine.controller;

import com.example.searchengine.model.Article;
import com.example.searchengine.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin
public class ArticleController {

    private final ArticleService service;

    public ArticleController(ArticleService service) {
        this.service = service;
    }

    @PostMapping
    public void addArticle(@RequestBody Article article) throws IOException {
        service.saveArticle(article);
    }

    @GetMapping("/search")
    public List<Article> searchArticles(@RequestParam String query) throws IOException {
        return service.search(query);
    }
}
