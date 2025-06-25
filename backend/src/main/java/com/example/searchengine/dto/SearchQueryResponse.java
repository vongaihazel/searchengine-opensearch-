package com.example.searchengine.dto;

import com.example.searchengine.model.Article;
import java.util.List;

/**
 * Data Transfer Object (DTO) representing the response of a search operation.
 * <p>
 * Contains a list of articles returned by the search and the total number of hits.
 * </p>
 */
public class SearchQueryResponse {

    /** List of articles matching the search request. */
    private List<Article> articles;

    /** Total number of matching articles found. */
    private long totalHits;

    /**
     * Constructs a new SearchQueryResponse.
     *
     * @param articles list of articles
     * @param totalHits total hits count
     */
    public SearchQueryResponse(List<Article> articles, long totalHits) {
        this.articles = articles;
        this.totalHits = totalHits;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public long getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(long totalHits) {
        this.totalHits = totalHits;
    }
}
