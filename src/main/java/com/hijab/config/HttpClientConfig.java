package com.hijab.config;

import com.hijab.external.exchange.PcaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpClientConfig {
    @Bean
    public PcaClient fileUploadClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl("http://127.0.0.1:5000")
                .build();

        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(PcaClient.class);
    }
}