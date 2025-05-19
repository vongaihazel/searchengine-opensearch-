package com.example.searchengine.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing the response of a search operation.
 * <p>
 * Contains the search query string and the timestamp when the search was performed.
 * </p>
 */
public class SearchResponse {

    /** The search query string. */
    private String query;

    /** The timestamp when the search was executed. */
    private LocalDateTime timestamp;

    /**
     * Default constructor.
     */
    public SearchResponse() {
    }

    /**
     * Constructs a new {@code SearchResponse} with the specified query and timestamp.
     *
     * @param query     the search query string
     * @param timestamp the time when the search was performed
     */
    public SearchResponse(String query, LocalDateTime timestamp) {
        this.query = query;
        this.timestamp = timestamp;
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

    /**
     * Gets the timestamp when the search was performed.
     *
     * @return the search timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp for the search.
     *
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
