package io.github.kszapsza.springairag.adapter.application;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.kszapsza.springairag.adapter.llm.ChatProperties;
import io.github.kszapsza.springairag.domain.chat.ChatProvider;
import io.github.kszapsza.springairag.domain.chat.ChatService;

@Configuration
@EnableConfigurationProperties(ChatProperties.class)
public class ApplicationConfiguration {

    @Bean
    ChatService chatService(ChatProvider chatProvider) {
        return new ChatService(chatProvider);
    }
}
