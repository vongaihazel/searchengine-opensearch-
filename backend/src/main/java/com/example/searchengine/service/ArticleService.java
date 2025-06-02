package com.example.searchengine.service;

import com.example.searchengine.model.Article;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.FieldValue;
import org.opensearch.client.opensearch.core.IndexRequest;
import org.opensearch.client.opensearch.core.SearchRequest;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.search.Hit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing articles in OpenSearch.
 * <p>
 * Provides methods to save (index) and search articles in the "articles" index.
 */
@Service
public class ArticleService {

    @Autowired
    private OpenSearchClient client;

    private static final String INDEX = "articles";

    /**
     * Indexes a new article into the OpenSearch "articles" index.
     * If an article with the same ID exists, it will be updated.
     *
     * @param article the {@link Article} to index
     * @throws IOException if indexing fails
     */
    public void saveArticle(Article article) throws IOException {
        IndexRequest<Article> request = IndexRequest.of(i -> i
                .index(INDEX)
                .id(article.getId())
                .document(article)
        );
        client.index(request);
    }

    /**
     * Searches the "articles" index for articles that match the given query string
     * in the "content" field.
     *
     * @param query the search term to match against article content
     * @return a list of matching {@link Article} objects
     * @throws IOException if the search request fails
     */
    public List<Article> search(String query) throws IOException {
        SearchRequest request = SearchRequest.of(s -> s
                .index(INDEX)
                .query(q -> q
                        .match(m -> m
                                .field("content")
                                .query(FieldValue.of(query))
                        )
                )
        );

        SearchResponse<Article> response = client.search(request, Article.class);
        return response.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }
}
