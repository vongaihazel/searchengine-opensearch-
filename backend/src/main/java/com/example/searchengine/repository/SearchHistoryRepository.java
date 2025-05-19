package com.example.searchengine.repository;

import com.example.searchengine.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing {@link SearchHistory} entities.
 * <p>
 * Extends {@link JpaRepository} to provide standard CRUD operations.
 * Also includes a custom query method to retrieve search history by user ID.
 * </p>
 */
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

    /**
     * Finds all {@link SearchHistory} records associated with the specified user ID.
     *
     * @param userId the ID of the user whose search history should be retrieved
     * @return a list of {@link SearchHistory} records
     */
    List<SearchHistory> findByUserId(Long userId);
}
