package com.example.springbootelasticsearch.network.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.erhlc.RestClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages =  {"com.example.springbootelasticsearch.repository"})
@ConfigurationProperties(prefix = "elasticsearch")
public class RestClientConfig {

    @Value("${elasticsearch.url}")
    public String elasticsearchUrl;

    @Bean
    public RestHighLevelClient client() {
        final ClientConfiguration config= ClientConfiguration.builder()
                .connectedTo(elasticsearchUrl)
                .build();

        return RestClients.create(config).rest();
    }
}
