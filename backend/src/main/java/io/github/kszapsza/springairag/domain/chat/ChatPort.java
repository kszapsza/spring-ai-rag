package io.github.kszapsza.springairag.domain.chat;

public interface ChatPort {
    ChatResponse chat(ChatRequest request);
}
