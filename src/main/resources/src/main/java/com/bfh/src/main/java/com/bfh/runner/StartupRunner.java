package com.bfh.runner;

import com.bfh.model.GenerateWebhookResponse;
import com.bfh.service.SqlQuestionService;
import com.bfh.service.WebhookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {

    private final WebhookService webhookService;
    private final SqlQuestionService sqlQuestionService;

    public StartupRunner(WebhookService webhookService, SqlQuestionService sqlQuestionService) {
        this.webhookService = webhookService;
        this.sqlQuestionService = sqlQuestionService;
    }

    @Override
    public void run(String... args) {
        System.out.println("🚀 BFH Webhook Solver starting...");

        try {
            // 1) Call generateWebhook and get webhook URL + token
            GenerateWebhookResponse resp = webhookService.generateWebhook();
            if (resp == null) {
                System.err.println("ERROR: generateWebhook returned null.");
                return;
            }

            System.out.println("Received webhook: " + resp.getWebhook());

            // 2) Get final SQL query based on your regNo (you will set these in SqlQuestionService)
            String finalQuery = sqlQuestionService.getFinalSqlQuery();
            System.out.println("Final SQL to submit:\n" + finalQuery);

            // 3) Submit final query
            webhookService.submitFinalQuery(resp.getWebhook(), resp.getAccessToken(), finalQuery);

            System.out.println("✔ All done.");
        } catch (Exception e) {
            System.err.println("ERROR in startup runner:");
            e.printStackTrace();
        }

    }
}
