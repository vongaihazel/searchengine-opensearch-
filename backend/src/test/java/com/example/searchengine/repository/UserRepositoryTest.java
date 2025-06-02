package com.example.searchengine.repository;

import com.example.searchengine.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link UserRepository} using Spring Data JPA.
 * These tests ensure that User entities are correctly persisted and retrieved.
 */
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    /**
     * Sets up a test user in the database before each test case.
     */
    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("testuser");
        userRepository.save(user);
    }

    /**
     * Cleans up all user records after each test to maintain test isolation.
     */
    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    /**
     * Tests that a User entity is inserted and can be retrieved by its ID.
     * Validates both the presence and correctness of the data.
     */
    @Test
    public void testInsertUser() {
        User foundUser = userRepository.findById(user.getId()).orElse(null);

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("testuser");
    }
}
