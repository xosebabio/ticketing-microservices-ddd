package org.xbs.catalog.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.xbs.catalog.api.CatalogApi;

@Configuration
public class RestClientConfig {

    @Value("${catalog.api.base-url:http://localhost:8080}")
    private String baseUrl;

    @Bean
    public CatalogApi catalogApi() {
        RestClient restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();

        return HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build()
                .createClient(CatalogApi.class);
    }
}
