package com.example.searchengine.service;

import com.example.searchengine.model.SearchHistory;
import com.example.searchengine.model.User;
import com.example.searchengine.repository.SearchHistoryRepository;
import com.example.searchengine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchHistoryService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    // Save the search query to history
    public SearchHistory saveQuery(Long userId, String query) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        SearchHistory history = new SearchHistory();
        history.setUser(user);
        history.setQuery(query);
        history.setTimestamp(java.time.LocalDateTime.now());
        return searchHistoryRepository.save(history);
    }

    // Get the search history for a user (list of SearchHistory objects)
    public List<SearchHistory> getUserHistory(Long userId) {
        return searchHistoryRepository.findByUserId(userId);
    }
}
