package org.java.ollama.starter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OllamaRequestService {

    private final String modelType;
    private final String uri;
    private final boolean streamResponse;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OllamaRequestService(OllamaProperties ollamaProperties) {
        this.modelType = ollamaProperties.getModelType();
        this.uri = ollamaProperties.getUri();
        this.streamResponse = ollamaProperties.isStreamResponse();
    }

    public String sendRequest(String prompt) {
        OllamaGenerateRequest promptRequest  = new OllamaGenerateRequest(this.modelType, prompt, this.streamResponse);
        try {
            String payload = promptRequest.buildJsonPayload();
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return extractResponseField(response.body());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String extractResponseField(String responseBody) {
        try {
            JsonNode rootNode = this.objectMapper.readTree(responseBody);
            return rootNode.path("response").asText(null);
        } catch (Exception e) {
            System.out.println("Error extracting response field: " + e.getMessage());
            return null;
        }
    }
}
