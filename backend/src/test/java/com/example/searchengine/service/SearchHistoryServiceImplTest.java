package com.example.searchengine.service;

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

/**
 * Unit tests for {@link SearchHistoryServiceImpl} using Mockito.
 * This test class verifies the service logic for saving and retrieving search history.
 */
@ExtendWith(MockitoExtension.class)
public class SearchHistoryServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SearchHistoryRepository searchHistoryRepository;

    @InjectMocks
    private SearchHistoryServiceImpl searchHistoryServiceImpl;

    private User user;

    /**
     * Initializes common test data before each test case.
     */
    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
    }

    /**
     * Tests the {@code saveQuery} method to ensure a new search history entry is saved correctly.
     * Mocks the user lookup and verifies that the save operation is triggered once.
     */
    @Test
    public void testInsertQuery() {
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        searchHistoryServiceImpl.saveQuery(user.getId(), "example query");
        verify(searchHistoryRepository, times(1)).save(any(SearchHistory.class));
    }

    /**
     * Tests the {@code getUserHistory} method to verify retrieval of a user's search history.
     * Ensures the correct data is returned and the repository method is called once.
     */
    @Test
    public void testGetUserHistory() {
        SearchHistory history = new SearchHistory();
        history.setUser(user);
        history.setQuery("example query");

        when(searchHistoryRepository.findByUserId(1L)).thenReturn(List.of(history));

        List<SearchHistory> histories = searchHistoryServiceImpl.getUserHistory(1L);

        assertThat(histories).hasSize(1);
        assertThat(histories.get(0).getQuery()).isEqualTo("example query");
        verify(searchHistoryRepository, times(1)).findByUserId(1L);
    }
}
