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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private OpenSearchClient openSearchClient;

    private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);

    private static final String INDEX = "articles";

    public SearchQueryResponse searchArticles(SearchQueryRequest request) throws IOException {
        // ✅ Add debug log for incoming request
        logger.info("Received search request: query='{}', author='{}', category='{}', minRating='{}', maxRating='{}', minViews='{}', maxViews='{}', tags='{}'",
                request.getQuery(), request.getAuthor(), request.getCategory(), request.getMinRating(), request.getMaxRating(),
                request.getMinViews(), request.getMaxViews(), request.getTags());

        String queryText = request.getQuery();
        String author = request.getAuthor();
        String category = request.getCategory();
        Double minRating = request.getMinRating();
        Double maxRating = request.getMaxRating();
        Integer minViews = request.getMinViews();
        Integer maxViews = request.getMaxViews();
        List<String> tags = request.getTags();

        BoolQuery.Builder boolQuery = new BoolQuery.Builder();

        // Full-text search on title and content
        if (queryText != null && !queryText.isBlank()) {
            boolQuery.must(m -> m.multiMatch(mm -> mm
                    .fields("title", "content")
                    .query(queryText)
            ));
            logger.debug("Added multiMatch for query text: {}", queryText);
        }

        // Filter by author.keyword
        if (author != null && !author.isBlank()) {
            boolQuery.filter(f -> f.term(t -> t.field("author").value(FieldValue.of(author))));
            logger.debug("Added filter for author: {}", author);
        }

        // Filter by category.keyword
        if (category != null && !category.isBlank()) {
            boolQuery.filter(f -> f.term(t -> t.field("category").value(FieldValue.of(category))));
            logger.debug("Added filter for category: {}", category);
        }

        // Filter by tags (terms query)
        if (tags != null && !tags.isEmpty()) {
            boolQuery.filter(f -> f.terms(t -> t
                    .field("tags.keyword") // assuming tags have keyword subfield, if not, use "tags"
                    .terms(terms -> terms.value(tags.stream().map(FieldValue::of).toList()))
            ));
            logger.debug("Added filter for tags: {}", tags);
        }

        // Range filter for views
        if (minViews != null || maxViews != null) {
            boolQuery.filter(f -> f.range(r -> {
                r.field("views");
                if (minViews != null) r.gte(JsonData.of(minViews));
                if (maxViews != null) r.lte(JsonData.of(maxViews));
                return r;
            }));
            logger.debug("Added range filter for views: minViews={}, maxViews={}", minViews, maxViews);
        }

        // Range filter for rating (min and max)
        if (minRating != null || maxRating != null) {
            boolQuery.filter(f -> f.range(r -> {
                r.field("rating");
                if (minRating != null) r.gte(JsonData.of(minRating));
                if (maxRating != null) r.lte(JsonData.of(maxRating));
                return r;
            }));
            logger.debug("Added range filter for rating: minRating={}, maxRating={}", minRating, maxRating);
        }

        // Build the search request
        org.opensearch.client.opensearch.core.SearchRequest searchRequest = org.opensearch.client.opensearch.core.SearchRequest.of(s -> s
                .index(INDEX)
                .query(q -> q.bool(boolQuery.build()))
        );

        logger.debug("Built search request: {}", searchRequest);

        // Perform search
        org.opensearch.client.opensearch.core.SearchResponse<Article> searchResponse = openSearchClient.search(searchRequest, Article.class);

        List<Article> articles = searchResponse.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());

        long totalHits = searchResponse.hits().total().value();

        logger.info("Search returned {} total hits, {} articles fetched.", totalHits, articles.size());

        return new SearchQueryResponse(articles, totalHits);
    }
}
