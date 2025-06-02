package com.example.searchengine.controller;

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

/**
 * Unit test class for {@link SearchController}.
 * Uses Mockito and MockMvc to test the /search/query endpoint without starting a full application context.
 */
@ExtendWith(MockitoExtension.class)
public class SearchControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SearchHistoryServiceImpl searchHistoryServiceImpl;

    @InjectMocks
    private SearchController searchController;

    private User user;

    /**
     * Sets up the testing environment before each test.
     * Initializes MockMvc with the SearchController and creates a test user.
     */
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(searchController).build();

        user = new User();
        user.setId(1L);
        user.setUsername("testUser");
    }

    /**
     * Tests the /search/query POST endpoint.
     * Verifies that a search query is saved and the API responds with status 200 OK.
     *
     * @throws Exception in case of MockMvc failure
     */
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

        // Perform a POST request with the JSON body and expect HTTP 200 OK
        mockMvc.perform(post("/search/query")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(searchRequestJson))
                .andExpect(status().isOk());
    }
}
