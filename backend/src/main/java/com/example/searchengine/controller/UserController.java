package com.example.searchengine.controller;

import com.example.searchengine.entity.User;
import com.example.searchengine.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing user-related operations such as creating, retrieving,
 * updating, and deleting users, as well as listing users with search history.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;

    /**
     * Constructor for injecting the user service implementation.
     *
     * @param userService the user service to be injected
     */
    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    /**
     * Retrieves a list of all users.
     *
     * @return a list of all {@link User} entities
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Creates a new user.
     *
     * @param user the user entity to create
     * @return the saved {@link User} entity with HTTP 201 Created status
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.createUser(user);
        return ResponseEntity.status(201).body(savedUser);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return the {@link User} entity wrapped in a {@link ResponseEntity}
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Retrieves all users who have at least one search history entry.
     *
     * @return a list of {@link User} entities with search history
     */
    @GetMapping("/search-history")
    public List<User> getUsersWithSearchHistory() {
        return userService.findAllUsersWithSearchHistory();
    }

    /**
     * Updates an existing user.
     *
     * @param id   the ID of the user to update
     * @param user the updated user data
     * @return the updated {@link User} entity wrapped in a {@link ResponseEntity}
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     * @return a {@link ResponseEntity} with HTTP 204 No Content status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
