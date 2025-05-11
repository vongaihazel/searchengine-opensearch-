package com.example.searchengine.controller;

// Import necessary Spring annotations and classes
import com.example.searchengine.model.User;
import com.example.searchengine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Mark this class as a REST controller
@RestController
// Define the base URL for this controller
@RequestMapping("/users")
public class UserController {

    // Declare the UserService dependency
    private final UserService userService;

    // Constructor injection for UserService
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint to retrieve all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers(); // Return the list of users
    }

    // Endpoint to create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // Save the user and retrieve the saved User object
        User savedUser = userService.createUser(user);
        // Return the saved user with a 201 Created status
        return ResponseEntity.status(201).body(savedUser);
    }

    // Endpoint to retrieve a user by their ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        // Get the user by ID and return in the response
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // Endpoint to retrieve users who have search history
    @GetMapping("/search-history")
    public List<User> getUsersWithSearchHistory() {
        return userService.findAllUsersWithSearchHistory(); // Return users with search history
    }

    // Endpoint to update an existing user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        // Update the user and return the updated User object
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    // Endpoint to delete a user by their ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id); // Delete the user
        return ResponseEntity.noContent().build(); // Return 204 No Content status
    }
}