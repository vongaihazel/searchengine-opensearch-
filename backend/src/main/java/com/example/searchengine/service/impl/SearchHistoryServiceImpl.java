package com.example.searchengine.service.impl;

import com.example.searchengine.entity.SearchHistory;
import com.example.searchengine.entity.User;
import com.example.searchengine.repository.SearchHistoryRepository;
import com.example.searchengine.repository.UserRepository;
import com.example.searchengine.service.SearchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link SearchHistoryService} for managing user search history.
 */
@Service
public class SearchHistoryServiceImpl implements SearchHistoryService {

    /**
     * Repository for accessing user data.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Repository for managing search history records.
     */
    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    /**
     * Saves a search query for the given user.
     *
     * @param userId the ID of the user performing the search
     * @param query the search query string
     * @return the saved {@link SearchHistory} entity
     * @throws RuntimeException if the user with the specified ID does not exist
     */
    @Override
    public SearchHistory saveQuery(Long userId, String query) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        SearchHistory history = new SearchHistory();
        history.setUser(user);
        history.setQuery(query);
        history.setTimestamp(java.time.LocalDateTime.now());

        return searchHistoryRepository.save(history);
    }

    /**
     * Retrieves the search history of a user by their ID.
     *
     * @param userId the ID of the user
     * @return a list of {@link SearchHistory} entries associated with the user
     */
    @Override
    public List<SearchHistory> getUserHistory(Long userId) {
        return searchHistoryRepository.findByUserId(userId);
    }
}
