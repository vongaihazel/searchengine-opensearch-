package com.example.searchengine.service;

// Import necessary classes and annotations
import com.example.searchengine.model.SearchHistory;
import com.example.searchengine.model.User;
import com.example.searchengine.repository.SearchHistoryRepository;
import com.example.searchengine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Mark this class as a service component
@Service
public class SearchHistoryService {

    // Inject the UserRepository dependency
    @Autowired
    private UserRepository userRepository;

    // Inject the SearchHistoryRepository dependency
    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    // Method to save the search query to history
    public SearchHistory saveQuery(Long userId, String query) {
        // Retrieve the user by ID, throw an exception if not found
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create a new SearchHistory object
        SearchHistory history = new SearchHistory();
        history.setUser(user); // Set the associated user
        history.setQuery(query); // Set the search query
        history.setTimestamp(java.time.LocalDateTime.now()); // Set the current timestamp

        // Save the search history to the repository and return it
        return searchHistoryRepository.save(history);
    }

    // Method to get the search history for a user
    public List<SearchHistory> getUserHistory(Long userId) {
        // Retrieve and return the list of SearchHistory objects for the user
        return searchHistoryRepository.findByUserId(userId);
    }
}