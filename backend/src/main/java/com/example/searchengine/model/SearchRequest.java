package com.example.searchengine.model;

// This class represents a request for a search query
public class SearchRequest {
    // ID of the user making the search request
    private Long userId;

    // The search query string
    private String query;

    // Getters and Setters for the fields

    public Long getUserId() {
        return userId; // Return the user ID
    }

    public void setUserId(Long userId) {
        this.userId = userId; // Set the user ID
    }

    public String getQuery() {
        return query; // Return the search query
    }

    public void setQuery(String query) {
        this.query = query; // Set the search query
    }
}