package io.github.kszapsza.springairag.domain.chat;

public sealed interface ChatResponse {
    record Success(String content) implements ChatResponse {
    }

    record Failure(String errorMessage) implements ChatResponse {
    }
}
