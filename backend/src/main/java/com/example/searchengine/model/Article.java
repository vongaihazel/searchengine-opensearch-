package com.example.searchengine.model;

import java.util.List;

public class Article {
    private String id;
    private String title;
    private String content;
    private String category;
    private String author;
    private String publish_date; // or java.time.LocalDate if you add deserialization
    private List<String> tags;
    private int views;
    private int rating;

    public Article() {}

    // getters and setters for all fields below
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getPublish_date() { return publish_date; }
    public void setPublish_date(String publish_date) { this.publish_date = publish_date; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public int getViews() { return views; }
    public void setViews(int views) { this.views = views; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
}
