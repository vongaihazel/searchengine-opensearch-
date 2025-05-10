package com.example.searchengine.repository;

import com.example.searchengine.model.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    // Get history by userId (you don't need the findByUser method)
    List<SearchHistory> findByUserId(Long userId);
}
