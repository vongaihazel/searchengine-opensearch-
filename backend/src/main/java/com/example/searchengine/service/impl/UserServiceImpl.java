package com.example.searchengine.service.impl;

import com.example.searchengine.entity.User;
import com.example.searchengine.repository.UserRepository;
import com.example.searchengine.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link UserService} interface for managing user-related operations.
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * Repository for user data operations.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * EntityManager for executing custom JPQL queries.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Creates and saves a new user in the database.
     *
     * @param user the {@link User} object to create
     * @return the created and saved {@link User}
     */
    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Retrieves all users from the database.
     *
     * @return a list of all {@link User} entities
     */
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return the {@link User} with the given ID
     * @throws RuntimeException if the user is not found
     */
    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Updates an existing user’s information.
     *
     * @param id the ID of the user to update
     * @param user the new user data
     * @return the updated {@link User}
     * @throws RuntimeException if the user is not found
     */
    @Override
    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.setUsername(user.getUsername());
        return userRepository.save(existingUser);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     */
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Retrieves all users along with their associated search history.
     *
     * @return a list of {@link User} entities with their search history eagerly loaded
     */
    @Override
    public List<User> findAllUsersWithSearchHistory() {
        String jpql = "SELECT u FROM User u LEFT JOIN FETCH u.searchHistory";
        return entityManager.createQuery(jpql, User.class).getResultList();
    }
}
