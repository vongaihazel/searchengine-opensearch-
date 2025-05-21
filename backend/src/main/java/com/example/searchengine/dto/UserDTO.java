package com.example.searchengine.dto;

/**
 * Data Transfer Object for transferring user data between the frontend and backend.
 * <p>
 * Contains only fields that are necessary for client interactions.
 * </p>
 */
public class UserDTO {

    /** The unique identifier of the user (optional for creation). */
    private Long id;

    /** The username of the user. */
    private String username;

    /** The email address of the user. */
    private String email;

    /**
     * Gets the user's ID.
     *
     * @return the user ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the user's ID.
     *
     * @param id the ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the email address.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
