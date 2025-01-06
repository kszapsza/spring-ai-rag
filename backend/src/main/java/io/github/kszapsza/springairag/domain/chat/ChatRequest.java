package io.github.kszapsza.springairag.domain.chat;

import io.github.kszapsza.springairag.domain.chat.memory.ConversationId;

public record ChatRequest(ConversationId conversationId, String message) {
}
