package io.github.kszapsza.springairag.adapter.llm.memory;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ChatMemoryConfiguration {

    @Bean
    ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }
}
