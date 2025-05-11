package com.example.searchengine.repository;

// Import necessary classes
import com.example.searchengine.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Mark this interface as a repository
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Method to retrieve a user by ID, returning an Optional
    Optional<User> findById(Long id); // This method is provided by JpaRepository by default
}