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

@Configuration
public class OpenSearchConfig {

    private final OpenSearchConfigProperties config;

    public OpenSearchConfig(OpenSearchConfigProperties config) {
        this.config = config;
    }

    @Bean
    public OpenSearchClient openSearchClient() {
        try {
            // Trust all certs (for development only)
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                        public void checkServerTrusted(X509Certificate[] certs, String authType) { }
                    }
            };
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

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

            JsonpMapper mapper = new JacksonJsonpMapper();
            RestClientTransport transport = new RestClientTransport(restClient, mapper);
            return new OpenSearchClient(transport);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create OpenSearch client", e);
        }
    }
}
