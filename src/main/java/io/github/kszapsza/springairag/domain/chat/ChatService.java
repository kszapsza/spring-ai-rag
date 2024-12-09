package io.github.kszapsza.springairag.domain.chat;

public class ChatService {
    private final ChatProvider chatProvider;

    public ChatService(ChatProvider chatProvider) {
        this.chatProvider = chatProvider;
    }

    public ChatResponse chat(ChatRequest request) {
        return chatProvider.chat(request);
    }
}
