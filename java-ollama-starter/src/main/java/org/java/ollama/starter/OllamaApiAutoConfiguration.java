package org.java.ollama.starter;

import org.java.ollama.starter.OllamaProperties;
import org.java.ollama.starter.OllamaRequestService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(OllamaProperties.class)
@ConditionalOnClass(OllamaRequestService.class)
@EnableAutoConfiguration
public class OllamaApiAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public OllamaRequestService ollamaService(OllamaProperties configProperties) {
        return new OllamaRequestService(
                configProperties
        );
    }
}
