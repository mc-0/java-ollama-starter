package org.java.ollama.starter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OllamaRequestService {

    private final String modelType;
    private final String uri;
    private final boolean streamResponse;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public OllamaRequestService(OllamaProperties ollamaProperties) {
        this.modelType = ollamaProperties.getModelType();
        this.uri = ollamaProperties.getUri();
        this.streamResponse = ollamaProperties.isStreamResponse();
    }

    public String sendRequest(String prompt) {
        OllamaGenerateRequest promptRequest  = new OllamaGenerateRequest(this.modelType, prompt, this.streamResponse);

        try {
            // Build the JSON payload

            String payload = promptRequest.buildJsonPayload();
            // Create the HTTP client
            HttpClient client = HttpClient.newHttpClient();

            System.out.println("*** EXECUTING HTTP POST ***");
            // Create the HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .header("Content-Type", "application/json")  // Set the header
                    .POST(HttpRequest.BodyPublishers.ofString(payload))  // Set the body
                    .build();

            // Send the request and handle the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse and handle the response
            String responseBody = response.body();

            // Extract the 'response' field from the JSON response
            String responseText = extractResponseField(responseBody);
            System.out.println("LLM Response: " + responseText);
            return responseText;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String extractResponseField(String responseBody) {
        try {
            // Parse the JSON response into a JsonNode tree
            JsonNode rootNode = this.objectMapper.readTree(responseBody);

            // Access the "response" field directly from the rootNode
            JsonNode responseFieldNode = rootNode.path("response");

            // Return the value of the "response" field as a string (null if not found)
            return responseFieldNode.asText(null);  // Or handle default value as needed
        } catch (Exception e) {
            // Handle error and return null if extraction fails
            System.out.println("Error extracting response field: " + e.getMessage());
            return null;
        }
    }

}
