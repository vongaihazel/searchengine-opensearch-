package com.example.searchengine.service;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.FieldValue;
import org.opensearch.client.opensearch.core.IndexRequest;
import org.opensearch.client.opensearch.core.SearchRequest;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.search.Hit;
import com.example.searchengine.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private OpenSearchClient client;

    private static final String INDEX = "articles";

    public void saveArticle(Article article) throws IOException {
        IndexRequest<Article> request = IndexRequest.of(i -> i
                .index(INDEX)
                .id(article.getId())
                .document(article)
        );
        client.index(request);
    }

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
