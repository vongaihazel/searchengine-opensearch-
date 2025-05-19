package com.example.searchengine.dto;

/**
 * Data Transfer Object (DTO) representing a search request made by a user.
 * <p>
 * Contains the user ID of the requester and the search query string.
 * </p>
 */
public class SearchRequest {

    /** The ID of the user making the search request. */
    private Long userId;

    /** The search query string submitted by the user. */
    private String query;

    /**
     * Gets the ID of the user making the request.
     *
     * @return the user ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the user making the request.
     *
     * @param userId the user ID to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets the search query string.
     *
     * @return the search query
     */
    public String getQuery() {
        return query;
    }

    /**
     * Sets the search query string.
     *
     * @param query the search query to set
     */
    public void setQuery(String query) {
        this.query = query;
    }
}
