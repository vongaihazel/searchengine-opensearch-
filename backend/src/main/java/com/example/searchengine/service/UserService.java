package com.example.searchengine.service;

import com.example.searchengine.entity.User;

import java.util.List;

/**
 * Service interface for managing user-related operations.
 */
public interface UserService {

    /**
     * Creates and saves a new user.
     *
     * @param user the {@link User} entity to be created
     * @return the saved {@link User}
     */
    User createUser(User user);

    /**
     * Retrieves all users from the system.
     *
     * @return a list of all {@link User} entities
     */
    List<User> getAllUsers();

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user
     * @return the {@link User} with the given ID
     * @throws RuntimeException if the user is not found
     */
    User getUserById(Long id);

    /**
     * Updates an existing user's information.
     *
     * @param id the ID of the user to update
     * @param user the updated {@link User} object
     * @return the updated {@link User}
     * @throws RuntimeException if the user is not found
     */
    User updateUser(Long id, User user);

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     */
    void deleteUser(Long id);

    /**
     * Finds all users along with their search history records.
     *
     * @return a list of {@link User} entities that include search history
     */
    List<User> findAllUsersWithSearchHistory();
}
