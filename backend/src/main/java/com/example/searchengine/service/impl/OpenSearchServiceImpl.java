package com.example.searchengine.service.impl;

import com.example.searchengine.model.Article;
import com.example.searchengine.service.OpenSearchService;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.FieldValue;
import org.opensearch.client.opensearch.core.IndexRequest;
import org.opensearch.client.opensearch.core.SearchRequest;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.search.Hit;
import org.opensearch.client.opensearch._types.query_dsl.MatchQuery;
import org.opensearch.client.opensearch._types.query_dsl.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class OpenSearchServiceImpl implements OpenSearchService {

    @Autowired
    private OpenSearchClient client;

    /**
     * Performs a search operation using OpenSearch Java client.
     *
     * @param query the search term
     * @return the SearchResponse object containing matching results
     * @throws Exception if the search fails
     */
    @Override
    public SearchResponse<Article> search(String query) throws Exception {
        SearchRequest request = SearchRequest.of(s -> s
                .index("articles") // Your index name
                .query(q -> q
                        .match(m -> m
                                .field("content")
                                .query(FieldValue.of(query))
                        )
                )
        );

        return client.search(request, Article.class);
    }

    public void indexArticle(Article article) throws IOException {
        IndexRequest<Article> request = new IndexRequest.Builder<Article>()
                .index("articles")
                .id(article.getId())
                .document(article)
                .build();
        client.index(request);

    }

}
