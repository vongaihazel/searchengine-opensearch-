package com.example.searchengine.repository;

// Import necessary classes for testing
import com.example.searchengine.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

// Mark this class for JPA testing
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository; // Repository for User

    private User user; // Test user object

    @BeforeEach
    public void setUp() {
        // Initialize and save a test user before each test
        user = new User();
        user.setUsername("testuser");
        userRepository.save(user);
    }

    @AfterEach
    public void tearDown() {
        // Clean up the database after each test
        userRepository.deleteAll();
    }

    @Test
    public void testInsertUser() {
        // Retrieve the user by ID
        User foundUser = userRepository.findById(user.getId()).orElse(null);

        // Assertions to verify the saved user
        assertThat(foundUser).isNotNull(); // Check that the user is found
        assertThat(foundUser.getUsername()).isEqualTo("testuser"); // Check the username
    }
}