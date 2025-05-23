package com.example.searchengine.controller;

import com.example.searchengine.dto.UserDTO;
import com.example.searchengine.entity.User;
import com.example.searchengine.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
     * @return a list of all {@link UserDTO} objects
     */
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Creates a new user.
     *
     * @param userDTO the user data to create
     * @return the created user as a {@link UserDTO}
     */
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User createdUser = userService.createUser(convertToEntity(userDTO));
        return ResponseEntity.status(201).body(convertToDTO(createdUser));
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id the ID of the user to retrieve
     * @return the user as a {@link UserDTO}
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(convertToDTO(user));
    }

    /**
     * Retrieves all users who have at least one search history entry.
     *
     * @return a list of {@link UserDTO} entities with search history
     */
    @GetMapping("/search-history")
    public List<UserDTO> getUsersWithSearchHistory() {
        return userService.findAllUsersWithSearchHistory()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing user.
     *
     * @param id      the ID of the user to update
     * @param userDTO the updated user data
     * @return the updated {@link UserDTO} object
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        User updatedUser = userService.updateUser(id, convertToEntity(userDTO));
        return ResponseEntity.ok(convertToDTO(updatedUser));
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

    /**
     * Converts a {@link User} entity to a {@link UserDTO}.
     *
     * @param user the user entity to convert
     * @return the corresponding user DTO
     */
    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }

    /**
     * Converts a {@link UserDTO} to a {@link User} entity.
     *
     * @param dto the user DTO to convert
     * @return the corresponding user entity
     */
    private User convertToEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId()); // optional; skip this if creating new users only
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        return user;
    }

    /**
     * Authenticates a user based on username and email.
     *
     * @param userDTO the user credentials
     * @return the matched {@link UserDTO} or 401 Unauthorized if not found
     */
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserDTO userDTO) {
        User user = userService.findByUsernameAndEmail(userDTO.getUsername(), userDTO.getEmail());
        if (user != null) {
            return ResponseEntity.ok(convertToDTO(user));
        } else {
            return ResponseEntity.status(401).build(); // Unauthorized
        }
    }


}
