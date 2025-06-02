package com.example.searchengine.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Configuration properties class for OpenSearch connection settings.
 * <p>
 * This class is annotated with {@link ConfigurationProperties} and maps properties
 * prefixed with <code>opensearch</code> from the application's configuration files
 * (e.g., <code>application.yml</code> or <code>application.properties</code>).
 * </p>
 */
@Configuration
@ConfigurationProperties(prefix = "opensearch")
@Primary
public class OpenSearchConfigProperties {

    /**
     * The OpenSearch server host (e.g., localhost).
     */
    private String host;

    /**
     * The OpenSearch server port (e.g., 9200).
     */
    private int port;

    /**
     * The username used to authenticate with OpenSearch.
     */
    private String username;

    /**
     * The password used to authenticate with OpenSearch.
     */
    private String password;

    // Getters and setters

    /**
     * Returns the configured OpenSearch host.
     *
     * @return the OpenSearch host
     */
    public String getHost() {
        return host;
    }

    /**
     * Sets the OpenSearch host.
     *
     * @param host the OpenSearch host
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Returns the configured OpenSearch port.
     *
     * @return the OpenSearch port
     */
    public int getPort() {
        return port;
    }

    /**
     * Sets the OpenSearch port.
     *
     * @param port the OpenSearch port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Returns the OpenSearch username.
     *
     * @return the username for authentication
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the OpenSearch username.
     *
     * @param username the username for authentication
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the OpenSearch password.
     *
     * @return the password for authentication
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the OpenSearch password.
     *
     * @param password the password for authentication
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
