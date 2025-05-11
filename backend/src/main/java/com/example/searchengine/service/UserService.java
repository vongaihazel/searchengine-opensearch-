package com.example.searchengine.service;

// Import necessary classes and annotations
import com.example.searchengine.model.User;
import com.example.searchengine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

// Mark this class as a service component
@Service
public class UserService {

    // Inject the UserRepository dependency
    @Autowired
    private UserRepository userRepository;

    // Inject the EntityManager for JPA operations
    @PersistenceContext
    private EntityManager entityManager;

    // Method to create a new user
    public User createUser(User user) {
        return userRepository.save(user); // Save and return the new user
    }

    // Method to retrieve all users
    public List<User> getAllUsers() {
        return userRepository.findAll(); // Return the list of users
    }

    // Method to retrieve a user by ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found")); // Throw exception if user not found
    }

    // Method to update an existing user
    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found")); // Throw exception if user not found
        existingUser.setUsername(user.getUsername()); // Update the username
        // Update other fields from User if necessary
        return userRepository.save(existingUser); // Save and return the updated user
    }

    // Method to delete a user by ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id); // Delete the user
    }

    // New method to fetch users with search history
    public List<User> findAllUsersWithSearchHistory() {
        // JPQL query to fetch users along with their search history
        String jpql = "SELECT u FROM User u LEFT JOIN FETCH u.searchHistory";
        return entityManager.createQuery(jpql, User.class).getResultList(); // Execute the query and return results
    }
}