package com.carlos.portfolio.contact_service.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class EmailService {

    @Value("${resend.api.key}")
    private String apiKey;

    private static final String API_URL = "https://api.resend.com/emails";

    public void sendContactEmail(String userEmail, String name, String message) {

        String jsonBody = """
        {
          "from": "Portafolio <onboarding@resend.dev>",
          "to": ["carlos_sm90@outlook.com"],
          "reply_to": "%s",
          "subject": "Nuevo mensaje desde tu portafolio",
          "text": "Nombre: %s \\nEmail: %s\\n\\nMensaje:\\n%s"
        }
        """.formatted(userEmail, name, userEmail, message);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        try {
            HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException("Error sending email", e);
        }
    }
}