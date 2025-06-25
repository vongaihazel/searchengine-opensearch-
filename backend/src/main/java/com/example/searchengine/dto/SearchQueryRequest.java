package com.example.searchengine.dto;

/**
 * Data Transfer Object (DTO) representing a search request made by a user.
 * <p>
 * Contains the user ID of the requester and the search query string,
 * along with optional filters like author, category, and minimum rating.
 * </p>
 */
public class SearchQueryRequest {

    /** The ID of the user making the search request. */
    private Long userId;

    /** The search query string submitted by the user. */
    private String query;

    /** Optional filter for article author. */
    private String author;

    /** Optional filter for article category. */
    private String category;

    /** Optional filter for minimum article rating. */
    private Double minRating;

    // Getters and setters for all fields

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getMinRating() {
        return minRating;
    }

    public void setMinRating(Double minRating) {
        this.minRating = minRating;
    }
}
