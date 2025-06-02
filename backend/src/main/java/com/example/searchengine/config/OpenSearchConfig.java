package com.example.searchengine.config;

import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.apache.http.Header;
import org.opensearch.client.RestClient;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.transport.rest_client.RestClientTransport;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.opensearch.client.json.JsonpMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.Base64;

/**
 * Spring configuration class for setting up the OpenSearch client bean.
 * <p>
 * This class constructs a secure (but dev-friendly) {@link OpenSearchClient}
 * that uses basic authentication and ignores SSL verification (for development purposes only).
 * </p>
 */
@Configuration
public class OpenSearchConfig {

    private final OpenSearchConfigProperties config;

    /**
     * Constructor for injecting {@link OpenSearchConfigProperties}.
     *
     * @param config the configuration properties for OpenSearch
     */
    public OpenSearchConfig(OpenSearchConfigProperties config) {
        this.config = config;
    }

    /**
     * Creates and configures the {@link OpenSearchClient} bean with SSL support
     * and basic authentication.
     *
     * @return the configured {@link OpenSearchClient}
     */
    @Bean
    public OpenSearchClient openSearchClient() {
        try {
            // Trust all certificates (NOT for production use)
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                            // No-op
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                            // No-op
                        }
                    }
            };

            // Set up a permissive SSL context (dev only)
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Configure the low-level REST client
            RestClient restClient = RestClient.builder(
                            new HttpHost(config.getHost(), config.getPort(), "https"))
                    .setDefaultHeaders(new Header[]{
                            new BasicHeader("Authorization",
                                    "Basic " + Base64.getEncoder().encodeToString(
                                            (config.getUsername() + ":" + config.getPassword())
                                                    .getBytes(StandardCharsets.UTF_8)
                                    ))
                    })
                    .setHttpClientConfigCallback(httpClientBuilder ->
                            httpClientBuilder
                                    .setSSLContext(sslContext)
                                    .setSSLHostnameVerifier((hostname, session) -> true)
                    )
                    .build();

            // Use Jackson for JSON mapping
            JsonpMapper mapper = new JacksonJsonpMapper();
            RestClientTransport transport = new RestClientTransport(restClient, mapper);

            // Create and return the OpenSearch client
            return new OpenSearchClient(transport);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create OpenSearch client", e);
        }
    }
}
