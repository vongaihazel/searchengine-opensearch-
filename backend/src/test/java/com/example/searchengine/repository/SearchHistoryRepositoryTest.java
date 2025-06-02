package com.example.searchengine.repository;

import com.example.searchengine.entity.SearchHistory;
import com.example.searchengine.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link SearchHistoryRepository} using Spring Data JPA.
 * These tests validate that SearchHistory entities are correctly persisted and retrieved.
 */
@DataJpaTest
public class SearchHistoryRepositoryTest {

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    /**
     * Initializes and persists a test user before each test case.
     */
    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("testuser");
        userRepository.save(user);
    }

    /**
     * Cleans up all persisted data after each test to maintain isolation.
     */
    @AfterEach
    public void tearDown() {
        searchHistoryRepository.deleteAll();
        userRepository.deleteAll();
    }

    /**
     * Tests that a SearchHistory entry can be inserted and retrieved correctly for a user.
     * Verifies the number of entries and query content.
     */
    @Test
    public void testInsertSearchHistory() {
        SearchHistory history = new SearchHistory();
        history.setUser(user);
        history.setQuery("example query");
        history.setTimestamp(LocalDateTime.now());

        searchHistoryRepository.save(history);

        List<SearchHistory> histories = searchHistoryRepository.findByUserId(user.getId());

        assertThat(histories).hasSize(1);
        assertThat(histories.get(0).getQuery()).isEqualTo("example query");
    }
}
