package io.github.kszapsza.springairag.domain.chat.memory;

import io.github.kszapsza.springairag.domain.chat.ChatMessage;

import java.util.List;

public interface ChatMemoryPort {

    void add(ConversationId conversationId, ChatMessage message);

    void add(ConversationId conversationId, List<ChatMessage> messages);

    List<ChatMessage> get(ConversationId conversationId, int lastN);

    void clear(ConversationId conversationId);
}
