package com.example.searchengine.service;

import com.example.searchengine.model.Article;
import org.opensearch.client.opensearch.core.SearchResponse;

/**
 * Service interface for performing search operations using OpenSearch.
 */
public interface OpenSearchService {

    /**
     * Executes a search query against the OpenSearch index.
     *
     * @param query the search query string
     * @return a {@link SearchResponse} containing the search results
     * @throws Exception if an error occurs during the search operation
     */
    SearchResponse<Article> search(String query) throws Exception;
}
