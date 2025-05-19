package com.example.searchengine.repository;

import com.example.searchengine.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link User} entities.
 * <p>
 * Extends {@link JpaRepository} to provide standard CRUD operations.
 * </p>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Retrieves a {@link User} entity by its ID.
     * <p>
     * Although this method is already provided by {@link JpaRepository},
     * it can be explicitly declared here for clarity or future customization.
     * </p>
     *
     * @param id the ID of the user to retrieve
     * @return an {@link Optional} containing the found user, or empty if not found
     */
    Optional<User> findById(Long id); // Explicitly declared for clarity
}
