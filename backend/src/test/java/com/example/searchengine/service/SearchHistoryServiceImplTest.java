package com.example.searchengine.service;

// Import necessary classes for testing
import com.example.searchengine.entity.SearchHistory;
import com.example.searchengine.entity.User;
import com.example.searchengine.repository.SearchHistoryRepository;
import com.example.searchengine.repository.UserRepository;
import com.example.searchengine.service.impl.SearchHistoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

// Extend with Mockito for testing
@ExtendWith(MockitoExtension.class)
public class SearchHistoryServiceImplTest {

    @Mock
    private UserRepository userRepository; // Mocked user repository

    @Mock
    private SearchHistoryRepository searchHistoryRepository; // Mocked search history repository

    @InjectMocks
    private SearchHistoryServiceImpl searchHistoryServiceImpl; // Service under test

    private User user; // Test user object

    @BeforeEach
    public void setUp() {
        // Initialize the test user before each test
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
    }

    @Test
    public void testInsertQuery() {
        // Stub the user repository to return the test user
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));

        // Call the method under test
        searchHistoryServiceImpl.saveQuery(user.getId(), "example query");

        // Verify that the save method was called once on the repository
        verify(searchHistoryRepository, times(1)).save(any(SearchHistory.class));
    }

    @Test
    public void testGetUserHistory() {
        // Create a new SearchHistory object
        SearchHistory history = new SearchHistory();
        history.setUser(user);
        history.setQuery("example query");

        // Stub the search history repository to return the test history
        when(searchHistoryRepository.findByUserId(1L)).thenReturn(List.of(history));

        // Call the method under test
        List<SearchHistory> histories = searchHistoryServiceImpl.getUserHistory(1L);

        // Assertions to verify the results
        assertThat(histories).hasSize(1); // Check the size of history
        assertThat(histories.get(0).getQuery()).isEqualTo("example query"); // Check the query value

        // Verify that the findByUserId method was called once on the repository
        verify(searchHistoryRepository, times(1)).findByUserId(1L);
    }
}