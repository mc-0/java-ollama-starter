package org.java.ollama.starter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ollama.api")
@Data
public class OllamaProperties {
    private String modelType;
    private String uri;
    private boolean streamResponse;

}
