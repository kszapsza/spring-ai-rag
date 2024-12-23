package io.github.kszapsza.springairag.adapter.llm.memory;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Component;

import io.github.kszapsza.springairag.domain.chat.ChatMessage;
import io.github.kszapsza.springairag.domain.chat.memory.ChatMemoryPort;
import io.github.kszapsza.springairag.domain.chat.memory.ConversationId;

@Component
public class ChatMemoryProvider implements ChatMemoryPort {

    private static final Logger logger = LoggerFactory.getLogger(ChatMemoryProvider.class);

    private final ChatMemory chatMemory;

    public ChatMemoryProvider(ChatMemory chatMemory) {
        this.chatMemory = chatMemory;
    }

    @Override
    public void add(ConversationId conversationId, ChatMessage message) {
        logger.debug("Adding 1 message to conversation with ID {}", conversationId.raw());
        chatMemory.add(
                conversationId.raw(),
                List.of(new UserMessage(message.content())));
    }

    @Override
    public void add(ConversationId conversationId, List<ChatMessage> messages) {
        logger.debug("Adding {} message(s) to conversation with ID {}",
                messages.size(), conversationId.raw());
        chatMemory.add(
                conversationId.raw(),
                messages.stream().map(this::toSpringAiMessage).toList());
    }

    @Override
    public List<ChatMessage> get(ConversationId conversationId, int lastN) {
        logger.debug("Retrieving last {} message(s) from conversation with ID {}",
                lastN, conversationId.raw());
        return chatMemory.get(conversationId.raw(), lastN).stream().map(this::toDomain).toList();
    }

    @Override
    public void clear(ConversationId conversationId) {
        logger.debug("Clearing the conversation with ID {}", conversationId.raw());
        chatMemory.clear(conversationId.raw());
    }

    private Message toSpringAiMessage(ChatMessage chatMessage) {
        return switch (chatMessage) {
            case ChatMessage.UserChatMessage message -> new UserMessage(message.content());
            case ChatMessage.AssistantChatMessage message -> new AssistantMessage(message.content());
        };
    }

    private ChatMessage toDomain(Message springAiMessage) {
        return switch (springAiMessage) {
            case UserMessage message -> new ChatMessage.UserChatMessage(message.getContent());
            case AssistantMessage message -> new ChatMessage.AssistantChatMessage(message.getContent());
            default -> throw new IllegalArgumentException(
                    "Unsupported message type: " + springAiMessage.getMessageType().name());
        };
    }
}
