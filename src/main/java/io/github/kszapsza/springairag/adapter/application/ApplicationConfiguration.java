package io.github.kszapsza.springairag.adapter.application;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import io.github.kszapsza.springairag.adapter.llm.ChatProperties;

@Configuration
@EnableConfigurationProperties(ChatProperties.class)
public class ApplicationConfiguration {
}
