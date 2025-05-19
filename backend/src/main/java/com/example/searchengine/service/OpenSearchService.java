package com.example.searchengine.service;

import org.opensearch.action.search.SearchResponse;

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
    SearchResponse search(String query) throws Exception;
}
