package com.example.searchengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Search Engine Spring Boot application.
 *
 * This class bootstraps the application by invoking {@link SpringApplication#run(Class, String...)}
 * which starts the embedded server and initializes the Spring application context.
 */
@SpringBootApplication
public class SearchengineApplication {

	/**
	 * Main method to launch the Spring Boot application.
	 *
	 * @param args command-line arguments (if any)
	 */
	public static void main(String[] args) {
		SpringApplication.run(SearchengineApplication.class, args);
	}
}
