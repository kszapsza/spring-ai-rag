package io.github.kszapsza.springairag.adapter.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.kszapsza.springairag.domain.chat.ChatRequest;
import io.github.kszapsza.springairag.domain.chat.ChatResponse;
import io.github.kszapsza.springairag.domain.chat.ChatService;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<ChatResponseDto> chat(@RequestBody ChatRequestDto request) {
        var chatResponse = chatService.chat(request.toDomain());

        return switch (chatResponse) {
            case ChatResponse.Success success ->
                ResponseEntity.ok(ChatResponseDto.fromDomain(success));
            case ChatResponse.Failure failure ->
                ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new ChatResponseDto("The chat is temporarily unavailable. Please try again later."));
        };
    }
}

record ChatRequestDto(String message) {
    public ChatRequest toDomain() {
        return new ChatRequest(message());
    }
}

record ChatResponseDto(String message) {
    public static ChatResponseDto fromDomain(ChatResponse.Success response) {
        return new ChatResponseDto(response.content());
    }
}
