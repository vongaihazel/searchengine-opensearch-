package com.example.searchengine.service;

import com.example.searchengine.dto.SearchQueryRequest;
import com.example.searchengine.dto.SearchQueryResponse;
import com.example.searchengine.model.Article;
import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.FieldValue;
import org.opensearch.client.opensearch._types.query_dsl.BoolQuery;
import org.opensearch.client.opensearch.core.SearchRequest;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.search.Hit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.opensearch.client.RestClient;
import org.opensearch.client.Request;
import org.opensearch.client.Response;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final OpenSearchClient openSearchClient;
    private final RestClient restClient;

    private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);
    private static final String INDEX = "articles";

    // Use constructor injection for both clients
    @Autowired
    public ArticleService(OpenSearchClient openSearchClient, RestClient restClient) {
        this.openSearchClient = openSearchClient;
        this.restClient = restClient;
    }

    public SearchQueryResponse searchArticles(SearchQueryRequest request) throws IOException {
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

        if (queryText != null && !queryText.isBlank()) {
            boolQuery.must(m -> m.multiMatch(mm -> mm
                    .fields("title", "content")
                    .query(queryText)
            ));
            logger.debug("Added multiMatch for query text: {}", queryText);
        }

        if (author != null && !author.isBlank()) {
            boolQuery.filter(f -> f.term(t -> t.field("author").value(FieldValue.of(author))));
            logger.debug("Added filter for author: {}", author);
        }

        if (category != null && !category.isBlank()) {
            boolQuery.filter(f -> f.term(t -> t.field("category").value(FieldValue.of(category))));
            logger.debug("Added filter for category: {}", category);
        }

        if (tags != null && !tags.isEmpty()) {
            boolQuery.filter(f -> f.terms(t -> t
                    .field("tags.keyword")
                    .terms(terms -> terms.value(tags.stream().map(FieldValue::of).toList()))
            ));
            logger.debug("Added filter for tags: {}", tags);
        }

        if (minViews != null || maxViews != null) {
            boolQuery.filter(f -> f.range(r -> {
                r.field("views");
                if (minViews != null) r.gte(JsonData.of(minViews));
                if (maxViews != null) r.lte(JsonData.of(maxViews));
                return r;
            }));
            logger.debug("Added range filter for views: minViews={}, maxViews={}", minViews, maxViews);
        }

        if (minRating != null || maxRating != null) {
            boolQuery.filter(f -> f.range(r -> {
                r.field("rating");
                if (minRating != null) r.gte(JsonData.of(minRating));
                if (maxRating != null) r.lte(JsonData.of(maxRating));
                return r;
            }));
            logger.debug("Added range filter for rating: minRating={}, maxRating={}", minRating, maxRating);
        }

        SearchRequest searchRequest = SearchRequest.of(s -> s
                .index(INDEX)
                .query(q -> q.bool(boolQuery.build()))
        );

        logger.debug("Built search request: {}", searchRequest);

        SearchResponse<Article> searchResponse = openSearchClient.search(searchRequest, Article.class);

        List<Article> articles = searchResponse.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());

        long totalHits = searchResponse.hits().total().value();

        logger.info("Search returned {} total hits, {} articles fetched.", totalHits, articles.size());

        return new SearchQueryResponse(articles, totalHits);
    }

    // Raw KNN vector search using RestClient
    public SearchQueryResponse searchByVector(List<Float> embedding, int kValue) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> knnQueryMap = Map.of(
                "knn", Map.of(
                        "field", "text_vector",
                        "query_vector", embedding,
                        "k", kValue,
                        "num_candidates", 100
                )
        );

        Map<String, Object> queryMap = Map.of("query", knnQueryMap, "size", kValue);
        String jsonRequestBody = objectMapper.writeValueAsString(queryMap);

        Request request = new Request("POST", "/" + INDEX + "/_search");
        request.setJsonEntity(jsonRequestBody);

        Response response = restClient.performRequest(request);

        Map<String, Object> responseMap = objectMapper.readValue(response.getEntity().getContent(), Map.class);

        List<Map<String, Object>> hits = (List<Map<String, Object>>)
                ((Map<String, Object>) responseMap.get("hits")).get("hits");

        List<Article> articles = hits.stream()
                .map(hit -> {
                    Map<String, Object> source = (Map<String, Object>) hit.get("_source");
                    return objectMapper.convertValue(source, Article.class);
                })
                .collect(Collectors.toList());

        Map<String, Object> hitsObject = (Map<String, Object>) responseMap.get("hits");
        Map<String, Object> totalObject = (Map<String, Object>) hitsObject.get("total");
        long totalHits = ((Number) totalObject.get("value")).longValue();

        return new SearchQueryResponse(articles, totalHits);
    }
}
