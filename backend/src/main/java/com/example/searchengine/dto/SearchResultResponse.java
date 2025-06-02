package com.example.searchengine.dto;

import com.example.searchengine.entity.SearchHistory;
import com.example.searchengine.model.Article;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SearchResultResponse {

    @JsonProperty("savedHistory")
    private SearchHistory savedHistory;

    @JsonProperty("openSearchResults")
    private List<Article> openSearchResults;

    public SearchResultResponse(SearchHistory savedHistory, List<Article> openSearchResults) {
        this.savedHistory = savedHistory;
        this.openSearchResults = openSearchResults;
    }

    public SearchHistory getSavedHistory() {
        return savedHistory;
    }

    public List<Article> getOpenSearchResults() {
        return openSearchResults;
    }
}
