package com.example.searchengine.controller;

import com.example.searchengine.entity.User;
import com.example.searchengine.service.impl.UserServiceImpl;
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

/**
 * Unit tests for {@link UserController} using JUnit and Mockito.
 * Tests cover the REST endpoints for retrieving and creating users.
 */
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    private User user;

    /**
     * Sets up MockMvc and test data before each test case.
     * Initializes a test user and configures the controller under test.
     */
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        user = new User();
        user.setId(1L);
        user.setUsername("testUser");
    }

    /**
     * Tests the GET /users endpoint.
     * Mocks the service response and verifies that the status returned is 200 OK.
     *
     * @throws Exception if the request fails
     */
    @Test
    public void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(user));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    /**
     * Tests the POST /users endpoint for creating a user.
     * Mocks the service to return the user and verifies that the response status is 201 Created.
     *
     * @throws Exception if the request fails
     */
    @Test
    public void testCreateUser() throws Exception {
        when(userService.createUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"newUser\"}"))
                .andExpect(status().isCreated());
    }
}
