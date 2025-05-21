package com.example.searchengine.controller;

// Import necessary classes for testing
import com.example.searchengine.entity.SearchHistory;
import com.example.searchengine.entity.User;
import com.example.searchengine.service.impl.SearchHistoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Extend with Mockito for testing
@ExtendWith(MockitoExtension.class)
public class SearchControllerTest {

    private MockMvc mockMvc; // MockMvc instance for testing controller

    @Mock
    private SearchHistoryServiceImpl searchHistoryServiceImpl; // Mocked service

    @InjectMocks
    private SearchController searchController; // Controller to test

    private User user; // Test user object

    @BeforeEach
    public void setUp() {
        // Set up MockMvc with the controller
        mockMvc = MockMvcBuilders.standaloneSetup(searchController).build();

        // Initialize the test user
        user = new User();
        user.setId(1L);
        user.setUsername("testUser");
    }

    @Test
    public void testSaveQuery() throws Exception {
        // Create a SearchHistory object to be returned by the mocked service
        SearchHistory savedHistory = new SearchHistory();
        savedHistory.setId(1L);
        savedHistory.setUser(user);
        savedHistory.setQuery("test query");

        // Mock the service's saveQuery method
        when(searchHistoryServiceImpl.saveQuery(1L, "test query")).thenReturn(savedHistory);

        // Create a JSON string for the SearchRequest
        String searchRequestJson = "{\"userId\": 1, \"query\": \"test query\"}";

        // Perform a POST request with the JSON body
        mockMvc.perform(post("/search/query")
                        .contentType(MediaType.APPLICATION_JSON) // Set content type to JSON
                        .content(searchRequestJson)) // Send JSON request body
                .andExpect(status().isOk()); // Expect HTTP 200 OK
    }
}