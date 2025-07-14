package com.example.searchengine.dto;

import java.util.List;

public class VectorSearchRequest {
    private List<Float> embedding;
    private int k;

    // Getters and setters
    public List<Float> getEmbedding() { return embedding; }
    public void setEmbedding(List<Float> embedding) { this.embedding = embedding; }
    public int getK() { return k; }
    public void setK(int k) { this.k = k; }
}
