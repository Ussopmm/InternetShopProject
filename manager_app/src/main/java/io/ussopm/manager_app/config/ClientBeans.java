package io.ussopm.manager_app.config;

import io.ussopm.manager_app.client.RestClientProductRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {

    @Bean
    public RestClientProductRestClient restClient(
            @Value("${shop.services.uri:http://localhost:8081}") String shopBaseUri) {
        return new RestClientProductRestClient(RestClient.builder()
                .baseUrl(shopBaseUri)
                .build());
    }
}
