package com.example.searchengine.service.impl;

import com.example.searchengine.service.OpenSearchService;
import org.opensearch.action.search.SearchRequest;
import org.opensearch.action.search.SearchResponse;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.index.query.QueryBuilders;
import org.opensearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implementation for interacting with OpenSearch.
 * <p>
 * Provides functionality to perform search operations using OpenSearch.
 * </p>
 */
@Service
public class OpenSearchServiceImpl implements OpenSearchService {

    /**
     * OpenSearch REST high-level client injected by Spring.
     */
    @Autowired
    private RestHighLevelClient client;

    /**
     * Performs a search operation against the "my_index" index using the provided query.
     *
     * @param query the search term to match against the "username" field
     * @return the {@link SearchResponse} returned by OpenSearch
     * @throws Exception if the search request fails
     */
    @Override
    public SearchResponse search(String query) throws Exception {
        SearchRequest searchRequest = new SearchRequest("my_index"); // Replace with actual index name if needed
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchQuery("username", query));
        searchRequest.source(sourceBuilder);

        return client.search(searchRequest, RequestOptions.DEFAULT);
    }
}
