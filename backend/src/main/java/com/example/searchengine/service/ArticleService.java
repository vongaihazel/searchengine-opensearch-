package com.example.searchengine.service;

import com.example.searchengine.dto.SearchQueryRequest;
import com.example.searchengine.dto.SearchQueryResponse;
import com.example.searchengine.model.Article;
import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.FieldValue;
import org.opensearch.client.opensearch.core.search.Hit;
import org.opensearch.client.opensearch._types.query_dsl.BoolQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private OpenSearchClient openSearchClient;

    private static final String INDEX = "articles";

    public SearchQueryResponse searchArticles(SearchQueryRequest request) throws IOException {
        String queryText = request.getQuery();
        String author = request.getAuthor();
        String category = request.getCategory();
        Double minRating = request.getMinRating();

        BoolQuery.Builder boolQuery = new BoolQuery.Builder();

        if (queryText != null && !queryText.isEmpty()) {
            boolQuery.must(m -> m.multiMatch(mm -> mm
                    .fields("title", "content")
                    .query(queryText)
            ));
        }

        if (author != null && !author.isEmpty()) {
            boolQuery.filter(f -> f.term(t -> t.field("author.keyword").value(FieldValue.of(author))));
        }

        if (category != null && !category.isEmpty()) {
            boolQuery.filter(f -> f.term(t -> t.field("category.keyword").value(FieldValue.of(category))));
        }

        if (minRating != null) {
            boolQuery.filter(f -> f.range(r -> r.field("rating").gte(JsonData.of(minRating))));
        }


        // Use fully qualified OpenSearch SearchRequest here
        org.opensearch.client.opensearch.core.SearchRequest searchRequest = org.opensearch.client.opensearch.core.SearchRequest.of(s -> s
                .index(INDEX)
                .query(q -> q.bool(boolQuery.build()))
        );

        // Use fully qualified OpenSearch SearchResponse here
        org.opensearch.client.opensearch.core.SearchResponse<Article> searchResponse = openSearchClient.search(searchRequest, Article.class);

        List<Article> articles = searchResponse.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());

        long totalHits = searchResponse.hits().total().value();

        // Return your own DTO SearchQueryResponse (not the client one)
        return new SearchQueryResponse(articles, totalHits);
    }
}
