package com.example.searchengine.controller;

// Import necessary classes for testing
import com.example.searchengine.model.User;
import com.example.searchengine.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Extend with Mockito for testing
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc; // MockMvc instance for testing controller

    @Mock
    private UserService userService; // Mocked user service

    @InjectMocks
    private UserController userController; // Controller to test

    private User user; // Test user object

    @BeforeEach
    public void setUp() {
        // Set up MockMvc with the user controller
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        // Initialize the test user
        user = new User();
        user.setId(1L);
        user.setUsername("testUser");
    }

    @Test
    public void testGetAllUsers() throws Exception {
        // Mock the service's getAllUsers method
        when(userService.getAllUsers()).thenReturn(List.of(user));

        // Perform a GET request and check the response status
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk()); // Expect HTTP 200 OK
    }

    @Test
    public void testCreateUser() throws Exception {
        // Mock the service's createUser method
        when(userService.createUser(any(User.class))).thenReturn(user);

        // Perform a POST request to create a new user and check the response status
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON) // Set content type to JSON
                        .content("{\"username\": \"newUser\"}")) // User data in JSON format
                .andExpect(status().isCreated()); // Expect HTTP 201 Created
    }
}