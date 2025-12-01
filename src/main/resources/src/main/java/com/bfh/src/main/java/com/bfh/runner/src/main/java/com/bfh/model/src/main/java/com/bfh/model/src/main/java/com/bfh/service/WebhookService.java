package com.bfh.service;

import com.bfh.model.GenerateWebhookRequest;
import com.bfh.model.GenerateWebhookResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class WebhookService {

    private final WebClient webClient = WebClient.builder()
            .codecs(configurer -> { /* default */ })
            .build();

    /**
     * Calls the generateWebhook endpoint and returns parsed response.
     * Update the request fields (name, regNo, email) before calling.
     */
    public GenerateWebhookResponse generateWebhook() {
        GenerateWebhookRequest req = new GenerateWebhookRequest();
        // === CHANGE THESE to your details ===
        req.setName("Your Name Here");
        req.setRegNo("REG12347");
        req.setEmail("you@example.com");
        // ====================================

        String uri = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

        try {
            Mono<GenerateWebhookResponse> mono = webClient.post()
                    .uri(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(req)
                    .retrieve()
                    .bodyToMono(GenerateWebhookResponse.class);

            // block for up to 15 seconds
            return mono.block(Duration.ofSeconds(15));
        } catch (Exception e) {
            System.err.println("Error while calling generateWebhook: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Submits the final SQL query to the webhook URL with Authorization header (JWT).
     * If accessToken doesn't start with "Bearer ", it will prepend it.
     */
    public void submitFinalQuery(String webhookUrl, String accessToken, String finalQuery) {
        if (webhookUrl == null || accessToken == null) {
            System.err.println("submitFinalQuery: webhookUrl or accessToken is null");
            return;
        }

        // Ensure token is in "Bearer <token>" format
        String headerToken = accessToken.startsWith("Bearer ") ? accessToken : "Bearer " + accessToken;

        Map<String, String> body = new HashMap<>();
        body.put("finalQuery", finalQuery);

        try {
            String res = webClient.post()
                    .uri(webhookUrl)
                    .header("Authorization", headerToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(Duration.ofSeconds(15));

            System.out.println("Webhook response: " + res);
        } catch (Exception e) {
            System.err.println("Error while submitting final query: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
