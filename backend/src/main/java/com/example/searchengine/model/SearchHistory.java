package com.example.searchengine.model;

// Import necessary classes for date and JPA annotations
import java.time.LocalDateTime;
import jakarta.persistence.*;

// Mark this class as a JPA entity
@Entity
// Specify the table name (defaults to the class name if not specified)
@Table
public class SearchHistory {

    // Define the primary key with auto-generated value
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Establish a many-to-one relationship with the User entity
    @ManyToOne
    @JoinColumn(name = "user_id") // Specify the foreign key column name
    private User user;

    // Define the search query string
    private String query;

    // Define the timestamp of when the search occurred
    private LocalDateTime timestamp;

    // Getters and Setters for the fields

    public Long getId() {
        return id; // Return the ID of the search history
    }

    public void setId(Long id) {
        this.id = id; // Set the ID of the search history
    }

    public User getUser() {
        return user; // Return the associated User
    }

    public void setUser(User user) {
        this.user = user; // Set the associated User
    }

    public String getQuery() {
        return query; // Return the search query
    }

    public void setQuery(String query) {
        this.query = query; // Set the search query
    }

    public LocalDateTime getTimestamp() {
        return timestamp; // Return the timestamp of the search
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp; // Set the timestamp of the search
    }
}