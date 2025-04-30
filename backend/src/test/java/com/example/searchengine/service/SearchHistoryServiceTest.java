package com.example.searchengine.service;

import com.example.searchengine.model.SearchHistory;
import com.example.searchengine.model.User;
import com.example.searchengine.repository.SearchHistoryRepository;
import com.example.searchengine.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class SearchHistoryServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SearchHistoryRepository searchHistoryRepository;

    @InjectMocks
    private SearchHistoryService searchHistoryService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
    }

    @Test
    public void testInsertQuery() {
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));

        searchHistoryService.saveQuery(user.getId(), "example query");

        verify(searchHistoryRepository, times(1)).save(any(SearchHistory.class));
    }

    @Test
    public void testGetUserHistory() {
        SearchHistory history = new SearchHistory();
        history.setUser(user);
        history.setQuery("example query");

        // Stub the search history repository to return the test history
        when(searchHistoryRepository.findByUserId(1L)).thenReturn(List.of(history));

        // Call the method under test
        List<SearchHistory> histories = searchHistoryService.getUserHistory(1L);

        // Assert the results
        assertThat(histories).hasSize(1);
        assertThat(histories.get(0).getQuery()).isEqualTo("example query");

        // Verify that the search history repository method was called
        verify(searchHistoryRepository, times(1)).findByUserId(1L);
    }
}