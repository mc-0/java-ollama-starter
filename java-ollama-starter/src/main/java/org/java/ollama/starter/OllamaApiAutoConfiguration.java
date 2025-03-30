package org.java.ollama.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(OllamaProperties.class)
@ConditionalOnClass(OllamaRequestService.class)
@ConditionalOnProperty(prefix = "ollama.api", name = "enabled", havingValue = "true", matchIfMissing = true)
public class OllamaApiAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public OllamaRequestService ollamaService(OllamaProperties configProperties) {
        return new OllamaRequestService(configProperties);
    }
}
