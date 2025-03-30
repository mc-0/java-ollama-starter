package org.java.ollama.starter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class OllamaGenerateRequest {
    private String modelName;
    private String prompt;
    private boolean stream;

    public OllamaGenerateRequest(String modelName, String prompt, boolean stream) {
        this.modelName = modelName;
        this.prompt = prompt;
        this.stream = stream;
    }

    // Method to build the JSON payload
    public String buildJsonPayload() {
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("model", modelName);
            payload.put("prompt", prompt);
            payload.put("stream", stream);

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(payload);
        } catch (Exception e) {
            throw new RuntimeException("Failed to build JSON", e);
        }
    }
}
