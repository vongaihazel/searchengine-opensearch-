package com.example.searchengine.repository;

import com.example.searchengine.model.SearchHistory;
import com.example.searchengine.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    List<SearchHistory> findByUser(User user);
    List<SearchHistory> findByUserId(Long userId);
}