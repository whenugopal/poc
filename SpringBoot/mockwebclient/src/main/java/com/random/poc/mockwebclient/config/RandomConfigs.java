package com.random.poc.mockwebclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RandomConfigs {

    @Bean("webClient")
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
