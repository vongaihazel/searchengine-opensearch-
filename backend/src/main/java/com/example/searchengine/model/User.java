package com.example.searchengine.model;

// Import necessary JPA annotations and classes
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.List;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;

// Mark this class as a JPA entity
@Entity
// Specify the table name for this entity
@Table(name = "users")
public class User {

    // Define the primary key with auto-generated value
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The username of the user
    private String username;

    // Establish a one-to-many relationship with SearchHistory
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER) // Fetch history eagerly
    private List<SearchHistory> searchHistory;

    // Getters and Setters for the fields

    public Long getId() {
        return id; // Return the user's ID
    }

    public void setId(Long id) {
        this.id = id; // Set the user's ID
    }

    public String getUsername() {
        return username; // Return the username
    }

    public void setUsername(String username) {
        this.username = username; // Set the username
    }

    public List<SearchHistory> getSearchHistory() {
        return searchHistory; // Return the user's search history
    }

    public void setSearchHistory(List<SearchHistory> searchHistory) {
        this.searchHistory = searchHistory; // Set the user's search history
    }
}