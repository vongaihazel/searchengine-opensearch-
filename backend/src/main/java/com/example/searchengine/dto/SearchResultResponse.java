package com.example.searchengine.dto;

import com.example.searchengine.entity.SearchHistory;
import com.example.searchengine.model.Article;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Data Transfer Object (DTO) used to encapsulate the result of a search operation.
 * <p>
 * It includes the saved search history entry and the list of search results
 * returned from OpenSearch.
 * </p>
 */
public class SearchResultResponse {

    /**
     * The saved search history entry related to the user's query.
     */
    @JsonProperty("savedHistory")
    private SearchHistory savedHistory;

    /**
     * The list of articles returned from the OpenSearch query.
     */
    @JsonProperty("openSearchResults")
    private List<Article> openSearchResults;

    /**
     * Constructs a new SearchResultResponse with the given history and search results.
     *
     * @param savedHistory       the saved search history entry
     * @param openSearchResults  the list of articles returned from OpenSearch
     */
    public SearchResultResponse(SearchHistory savedHistory, List<Article> openSearchResults) {
        this.savedHistory = savedHistory;
        this.openSearchResults = openSearchResults;
    }

    /**
     * Returns the saved search history.
     *
     * @return the {@link SearchHistory} object
     */
    public SearchHistory getSavedHistory() {
        return savedHistory;
    }

    /**
     * Returns the list of articles retrieved from OpenSearch.
     *
     * @return a list of {@link Article} objects
     */
    public List<Article> getOpenSearchResults() {
        return openSearchResults;
    }
}
