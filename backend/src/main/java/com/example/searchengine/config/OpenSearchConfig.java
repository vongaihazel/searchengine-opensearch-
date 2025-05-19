package com.example.searchengine.config;

import org.opensearch.client.RestHighLevelClient;
import org.opensearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.http.HttpHost;

/**
 * Configuration class for setting up the OpenSearch client.
 * <p>
 * This configuration defines a {@link RestHighLevelClient} bean used to interact with an OpenSearch instance.
 * </p>
 */
@Configuration
public class OpenSearchConfig {

    /**
     * Creates and configures a {@link RestHighLevelClient} for communicating with the OpenSearch server.
     *
     * @return a configured instance of {@link RestHighLevelClient}
     */
    @Bean
    public RestHighLevelClient openSearchClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http") // OpenSearch Docker port
                )
        );
    }
}
