package com.example.searchengine.service.impl;

import com.example.searchengine.model.Article;
import com.example.searchengine.service.OpenSearchService;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.IndexRequest;
import org.opensearch.client.opensearch.core.SearchRequest;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch._types.query_dsl.MatchAllQuery;
import org.opensearch.client.opensearch._types.query_dsl.Query;
import org.opensearch.client.opensearch._types.query_dsl.TextQueryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OpenSearchServiceImpl implements OpenSearchService {

    @Autowired
    private OpenSearchClient client;

    /**
     * Performs a search operation using OpenSearch Java client.
     *
     * @param query the search term
     * @return the SearchQueryResponse object containing matching results
     * @throws Exception if the search fails
     */
    @Override
    public SearchResponse<Article> search(String query) throws Exception {
        Query queryBuilder;

        // If query is "*" or empty/null, perform a match_all query to get all documents
        if (query == null || query.trim().equals("*") || query.trim().isEmpty()) {
            queryBuilder = Query.of(q -> q.matchAll(MatchAllQuery.of(m -> m)));
        } else {
            // Normal multi-match query on several fields
            queryBuilder = Query.of(q -> q
                    .multiMatch(m -> m
                            .query(query)
                            .fields("title", "content", "category", "author", "tags") // Search across all relevant fields
                            .type(TextQueryType.BestFields) // Use BestFields strategy correctly
                    )
            );
        }

        SearchRequest request = SearchRequest.of(s -> s
                .index("articles")
                .query(queryBuilder)
        );

        return client.search(request, Article.class);
    }

    /**
     * Indexes an article document into the OpenSearch index.
     *
     * @param article the article to index
     * @throws IOException if indexing fails
     */
    public void indexArticle(Article article) throws IOException {
        IndexRequest<Article> request = new IndexRequest.Builder<Article>()
                .index("articles")
                .id(article.getId())
                .document(article)
                .build();
        client.index(request);
    }
}
