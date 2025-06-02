package com.example.searchengine.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Entity representing a record of a user's search history.
 * <p>
 * Each search history record stores the search query, the user who made the search,
 * and the timestamp of when the search was performed.
 * </p>
 */
@Entity
@Table(name = "search_history") // Optional: explicitly name the table
public class SearchHistory {

    /** Unique identifier for the search history record. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The user who performed the search. */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"searchHistory"})
    private User user;

    /** The search query string entered by the user. */
    private String query;

    /** The timestamp when the search was performed. */
    private LocalDateTime timestamp;

    /**
     * Gets the ID of this search history record.
     *
     * @return the ID of the record
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of this search history record.
     *
     * @param id the ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the user who performed the search.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user who performed the search.
     *
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the search query string.
     *
     * @return the query string
     */
    public String getQuery() {
        return query;
    }

    /**
     * Sets the search query string.
     *
     * @param query the query string to set
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * Gets the timestamp of the search.
     *
     * @return the timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp of the search.
     *
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
