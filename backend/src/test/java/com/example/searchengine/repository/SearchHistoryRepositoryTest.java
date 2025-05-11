package com.example.searchengine.repository;

// Import necessary classes for testing
import com.example.searchengine.model.SearchHistory;
import com.example.searchengine.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// Mark this class for JPA testing
@DataJpaTest
public class SearchHistoryRepositoryTest {

    @Autowired
    private SearchHistoryRepository searchHistoryRepository; // Repository for SearchHistory

    @Autowired
    private UserRepository userRepository; // Repository for User

    private User user; // Test user object

    @BeforeEach
    public void setUp() {
        // Initialize and save a test user before each test
        user = new User();
        user.setUsername("testuser");
        userRepository.save(user);
    }

    @AfterEach
    public void tearDown() {
        // Clean up the database after each test
        searchHistoryRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testInsertSearchHistory() {
        // Create a new SearchHistory object
        SearchHistory history = new SearchHistory();
        history.setUser(user); // Associate with the test user
        history.setQuery("example query"); // Set the search query
        history.setTimestamp(LocalDateTime.now()); // Set the current timestamp

        // Save the search history
        searchHistoryRepository.save(history);

        // Retrieve the search history for the user
        List<SearchHistory> histories = searchHistoryRepository.findByUserId(user.getId());

        // Assertions to verify the saved history
        assertThat(histories).hasSize(1); // Check that one record exists
        assertThat(histories.get(0).getQuery()).isEqualTo("example query"); // Check the query value
    }
}