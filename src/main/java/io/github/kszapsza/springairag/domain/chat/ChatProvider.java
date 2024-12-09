package io.github.kszapsza.springairag.domain.chat;

public interface ChatProvider {
    ChatResponse chat(ChatRequest request);
}
