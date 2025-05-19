package com.example.searchengine.service;

import com.example.searchengine.entity.SearchHistory;

import java.util.List;

/**
 * Service interface for managing search history operations.
 */
public interface SearchHistoryService {

    /**
     * Saves a search query made by a specific user.
     *
     * @param userId the ID of the user making the query
     * @param query the search query string
     * @return the saved {@link SearchHistory} entity
     */
    SearchHistory saveQuery(Long userId, String query);

    /**
     * Retrieves the search history for a specific user.
     *
     * @param userId the ID of the user
     * @return a list of {@link SearchHistory} records associated with the user
     */
    List<SearchHistory> getUserHistory(Long userId);
}
