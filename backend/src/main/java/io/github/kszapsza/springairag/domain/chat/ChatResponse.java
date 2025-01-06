package io.github.kszapsza.springairag.domain.chat;

public sealed interface ChatResponse {
    record Success(String content) implements ChatResponse {
    }

    sealed interface Error extends ChatResponse {
        record ClientError(String errorMessage) implements Error {
        }

        record ServerError(String errorMessage) implements Error {
        }
    }
}
