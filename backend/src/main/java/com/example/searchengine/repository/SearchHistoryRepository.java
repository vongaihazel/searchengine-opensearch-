package com.example.searchengine.repository;

// Import necessary classes
import com.example.searchengine.model.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Define a repository interface for SearchHistory entities
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    // Method to retrieve search history by user ID
    List<SearchHistory> findByUserId(Long userId);
}