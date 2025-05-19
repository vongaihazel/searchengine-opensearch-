package com.example.searchengine.entity;

import jakarta.persistence.*;
import java.util.List;

/**
 * Entity representing a user of the search engine system.
 * <p>
 * Each user has a unique ID, a username, and an associated list of search history entries.
 * </p>
 */
@Entity
@Table(name = "users")
public class User {

    /** Unique identifier for the user. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The username of the user. */
    private String username;

    /**
     * The list of search history entries associated with this user.
     * Eagerly fetched to immediately load all related search history records.
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<SearchHistory> searchHistory;

    /**
     * Gets the unique ID of the user.
     *
     * @return the user's ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique ID of the user.
     *
     * @param id the ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the username of the user.
     *
     * @return the user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the list of search history entries for the user.
     *
     * @return the user's search history
     */
    public List<SearchHistory> getSearchHistory() {
        return searchHistory;
    }

    /**
     * Sets the list of search history entries for the user.
     *
     * @param searchHistory the search history to set
     */
    public void setSearchHistory(List<SearchHistory> searchHistory) {
        this.searchHistory = searchHistory;
    }
}
