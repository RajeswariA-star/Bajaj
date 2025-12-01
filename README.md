# BFH Java Webhook Solver

This is a ready-to-run Spring Boot application that:
1. On startup, calls `generateWebhook` endpoint to get a webhook URL and accessToken.
2. Chooses the SQL question (odd/even regNo).
3. Submits the `finalQuery` to the returned webhook using the access token in Authorization header.

## How to configure

Edit `src/main/java/com/bfh/service/WebhookService.java`:
- Replace `req.setName(...)`, `req.setRegNo(...)`, `req.setEmail(...)` with your details.

Edit `src/main/java/com/bfh/service/SqlQuestionService.java`:
- Set `regNo` to your registration number.
- Paste your solved SQL query strings into `finalQueryIfOdd` and `finalQueryIfEven`.

## Build & run locally

```bash
# build
mvn clean package

# run the jar
java -jar target/bfh-java-webhook-solver-1.0.0.jar
