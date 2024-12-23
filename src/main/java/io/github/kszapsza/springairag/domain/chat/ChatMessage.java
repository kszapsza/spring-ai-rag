package io.github.kszapsza.springairag.domain.chat;

public sealed interface ChatMessage {

    String content();

    ChatMessageType type();

    record AssistantChatMessage(String content) implements ChatMessage {
        @Override
        public ChatMessageType type() {
            return ChatMessageType.ASSISTANT;
        }
    }

    record UserChatMessage(String content) implements ChatMessage {
        @Override
        public ChatMessageType type() {
            return ChatMessageType.USER;
        }
    }
}
