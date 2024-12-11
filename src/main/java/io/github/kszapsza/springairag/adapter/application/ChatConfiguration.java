package io.github.kszapsza.springairag.adapter.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.kszapsza.springairag.domain.chat.ChatProvider;
import io.github.kszapsza.springairag.domain.chat.ChatService;

@Configuration
public class ChatConfiguration {
    @Bean
    ChatService chatService(ChatProvider chatProvider) {
        return new ChatService(chatProvider);
    }
}
