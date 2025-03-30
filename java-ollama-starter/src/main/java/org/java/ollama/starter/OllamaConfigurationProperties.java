package org.java.ollama.starter;

public interface OllamaConfigurationProperties {
    String getModelType();
    String getUri();
    boolean isStreamResponse();
}
