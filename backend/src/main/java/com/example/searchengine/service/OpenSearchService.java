package com.example.searchengine.service;

import org.opensearch.action.search.SearchRequest;
import org.opensearch.action.search.SearchResponse;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.index.query.QueryBuilders;
import org.opensearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.opensearch.client.RequestOptions;

@Service
public class OpenSearchService {

    @Autowired
    private RestHighLevelClient client;

    public SearchResponse search(String query) throws Exception {
        SearchRequest searchRequest = new SearchRequest("my_index"); // Replace "my_index" with your index name

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("username", query)); // Replace "username" with your field name

        searchRequest.source(searchSourceBuilder);

        return client.search(searchRequest, RequestOptions.DEFAULT);
    }
}
