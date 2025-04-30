package com.example.searchengine.controller;

import com.example.searchengine.model.SearchHistory;
import com.example.searchengine.model.User;
import com.example.searchengine.service.SearchHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class SearchControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SearchHistoryService searchHistoryService;

    @InjectMocks
    private SearchController searchController;

    private User user;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(searchController).build();

        user = new User();
        user.setId(1L);
        user.setUsername("testUser");
    }

    @Test
    public void testSaveQuery() throws Exception {
        SearchHistory savedHistory = new SearchHistory();
        savedHistory.setId(1L);
        savedHistory.setUser(user);
        savedHistory.setQuery("test query");

        when(searchHistoryService.saveQuery(1L, "test query")).thenReturn(savedHistory);

        mockMvc.perform(post("/search/query")
                        .param("userId", "1")
                        .param("query", "test query"))
                .andExpect(status().isOk());
    }
}