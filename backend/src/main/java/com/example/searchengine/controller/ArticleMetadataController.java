package com.example.searchengine.controller;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.aggregations.Aggregate;
import org.opensearch.client.opensearch._types.aggregations.Aggregation;
import org.opensearch.client.opensearch._types.aggregations.TermsAggregation;
import org.opensearch.client.opensearch.core.SearchRequest;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.search.Hit;
import org.opensearch.client.opensearch.core.search.TotalHitsRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ArticleMetadataController {

    @Autowired
    private OpenSearchClient openSearchClient;

    private static final String INDEX = "articles";

    @GetMapping("/api/articles/authors")
    public List<String> getAllAuthors() throws IOException {
        SearchResponse<Void> response = openSearchClient.search(
                SearchRequest.of(s -> s
                        .index(INDEX)
                        .size(0)
                        .aggregations("unique_authors", a -> a
                                .terms(TermsAggregation.of(t -> t
                                        .field("author.keyword")
                                        .size(1000) // max number of distinct authors you expect
                                ))
                        )
                ),
                Void.class
        );

        Aggregate agg = response.aggregations().get("unique_authors");
        if (agg == null || agg.isSterms() == false) {
            return List.of();
        }
        return agg.sterms().buckets().array().stream()
                .map(bucket -> bucket.key())
                .collect(Collectors.toList());
    }

    @GetMapping("/api/articles/categories")
    public List<String> getAllCategories() throws IOException {
        SearchResponse<Void> response = openSearchClient.search(
                SearchRequest.of(s -> s
                        .index(INDEX)
                        .size(0)
                        .aggregations("unique_categories", a -> a
                                .terms(TermsAggregation.of(t -> t
                                        .field("category.keyword")
                                        .size(1000) // max number of distinct categories
                                ))
                        )
                ),
                Void.class
        );

        Aggregate agg = response.aggregations().get("unique_categories");
        if (agg == null || agg.isSterms() == false) {
            return List.of();
        }
        return agg.sterms().buckets().array().stream()
                .map(bucket -> bucket.key())
                .collect(Collectors.toList());
    }
}
