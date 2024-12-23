package io.github.kszapsza.springairag.adapter.llm;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.chat")
public record ChatProperties(
        Embedding embedding,
        SystemPrompt systemPrompt) {

    public record Embedding(String document) {
    }

    public record SystemPrompt(
            String resource,
            Map<String, Object> placeholders) {
    }
}
