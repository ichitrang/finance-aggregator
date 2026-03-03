package com.acme.finance.plaid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class PlaidClientConfig {

    @Bean
    public WebClient plaidWebClient(@Value("${plaid.baseUrl}") String baseUrl) {
        return WebClient.builder().baseUrl(baseUrl).build();
    }
}