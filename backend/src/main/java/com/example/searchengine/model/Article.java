package com.example.searchengine.model;

/**
 * Represents an article entity used in the search engine.
 * <p>
 * This model is used for indexing and retrieving documents
 * from OpenSearch. It includes basic fields such as id, title, and content.
 * </p>
 */
public class Article {

    /**
     * The unique identifier of the article (can be null if auto-generated).
     */
    private String id;

    /**
     * The title of the article.
     */
    private String title;

    /**
     * The main content/body of the article.
     */
    private String content;

    /**
     * Default constructor required for JSON deserialization and frameworks.
     */
    public Article() {}

    /**
     * Constructs an {@code Article} with the given id, title, and content.
     *
     * @param id      the unique identifier of the article
     * @param title   the title of the article
     * @param content the content of the article
     */
    public Article(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    /**
     * Returns the article ID.
     *
     * @return the article ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the article ID.
     *
     * @param id the article ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the title of the article.
     *
     * @return the article title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the article.
     *
     * @param title the article title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the content of the article.
     *
     * @return the article content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the article.
     *
     * @param content the article content
     */
    public void setContent(String content) {
        this.content = content;
    }
}
