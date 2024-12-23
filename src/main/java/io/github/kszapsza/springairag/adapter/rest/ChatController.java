package io.github.kszapsza.springairag.adapter.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.kszapsza.springairag.domain.chat.ChatPort;
import io.github.kszapsza.springairag.domain.chat.ChatRequest;
import io.github.kszapsza.springairag.domain.chat.ChatResponse;
import io.github.kszapsza.springairag.domain.chat.memory.ConversationId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private final ChatPort chatPort;

    public ChatController(ChatPort chatPort) {
        this.chatPort = chatPort;
    }

    @PostMapping
    public ResponseEntity<ChatResponseDto> chat(@Valid @RequestBody ChatRequestDto request) {
        var chatResponse = chatPort.chat(request.toDomain());

        return switch (chatResponse) {
            case ChatResponse.Success success -> {
                yield ResponseEntity.ok(ChatResponseDto.fromDomain(success));
            }
            case ChatResponse.Error.ClientError failure -> {
                logger.warn("Client error occurred: {}", failure.errorMessage());
                yield ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ChatResponseDto(failure.errorMessage()));
            }
            case ChatResponse.Error.ServerError failure -> {
                logger.error("Server error occurred: {}", failure.errorMessage());
                yield ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new ChatResponseDto("The chat is temporarily unavailable. Please try again later."));
            }
        };
    }
}

record ChatRequestDto(@NotBlank String conversationId, @NotBlank String message) {
    public ChatRequest toDomain() {
        return new ChatRequest(new ConversationId(conversationId()), message());
    }
}

record ChatResponseDto(String message) {
    public static ChatResponseDto fromDomain(ChatResponse.Success response) {
        return new ChatResponseDto(response.content());
    }
}
