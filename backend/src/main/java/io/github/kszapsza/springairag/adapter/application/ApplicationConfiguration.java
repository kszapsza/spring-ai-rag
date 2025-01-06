package io.github.kszapsza.springairag.adapter.application;

import io.github.kszapsza.springairag.adapter.llm.ChatProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ChatProperties.class)
public class ApplicationConfiguration {
}
