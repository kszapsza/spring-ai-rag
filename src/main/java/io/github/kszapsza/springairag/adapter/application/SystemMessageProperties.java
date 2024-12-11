package io.github.kszapsza.springairag.adapter.application;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "chat.system-message")
public record SystemMessageProperties(@NonNull Map<String, Object> placeholders) {
}
