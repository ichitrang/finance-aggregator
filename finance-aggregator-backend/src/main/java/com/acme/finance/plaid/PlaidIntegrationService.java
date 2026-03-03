package com.acme.finance.plaid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.*;

@Service
public class PlaidIntegrationService {

    private final WebClient plaid;
    private final String clientId;
    private final String secret;

    public PlaidIntegrationService(
            WebClient plaidWebClient,
            @Value("${plaid.clientId}") String clientId,
            @Value("${plaid.secret}") String secret
    ) {
        this.plaid = plaidWebClient;
        this.clientId = clientId;
        this.secret = secret;
    }

    /**
     * Exchanges Plaid public_token for a long-lived access_token.
     * In Plaid: /item/public_token/exchange
     */
    public ExchangeResult exchangePublicToken(String publicToken) {
        Map<String, Object> body = Map.of(
                "client_id", clientId,
                "secret", secret,
                "public_token", publicToken
        );

        // NOTE: This is "logic"; wire exact response fields per Plaid docs.
        Map resp = plaid.post()
                .uri("/item/public_token/exchange")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        String accessToken = (String) resp.get("access_token");
        String itemId = (String) resp.get("item_id");
        return new ExchangeResult(accessToken, itemId);
    }

    /**
     * Fetches transactions. For simplicity using /transactions/get (date range).
     * For real-time incremental: use /transactions/sync with cursor if available.
     */
    public List<Map<String, Object>> fetchTransactions(String accessToken, LocalDate from, LocalDate to) {
        Map<String, Object> options = Map.of("count", 500, "offset", 0);

        Map<String, Object> body = new HashMap<>();
        body.put("client_id", clientId);
        body.put("secret", secret);
        body.put("access_token", accessToken);
        body.put("start_date", from.toString());
        body.put("end_date", to.toString());
        body.put("options", options);

        Map resp = plaid.post()
                .uri("/transactions/get")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        // resp.transactions = [ ... ]
        return (List<Map<String, Object>>) resp.getOrDefault("transactions", List.of());
    }

    public record ExchangeResult(String accessToken, String itemId) {}
}