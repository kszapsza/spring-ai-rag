package io.github.kszapsza.springairag.adapter.rest;

import io.github.kszapsza.springairag.domain.chat.ChatMessage;
import io.github.kszapsza.springairag.domain.chat.ChatMessageType;
import io.github.kszapsza.springairag.domain.chat.memory.ChatMemoryPort;
import io.github.kszapsza.springairag.domain.chat.memory.ConversationId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/conversation")
class ConversationController {

    private final ChatMemoryPort chatMemoryPort;
    private final int maxLastN;

    ConversationController(
            ChatMemoryPort chatMemoryPort,
            @Value("${app.conversation.max-last-n}") int maxLastN) {
        this.chatMemoryPort = chatMemoryPort;
        this.maxLastN = maxLastN;
    }

    @GetMapping(path = "{conversationId}", produces = {APPLICATION_JSON_VALUE}, consumes = {APPLICATION_JSON_VALUE})
    ResponseEntity<ConversationHistoryDto> getConversationHistory(
            @PathVariable(name = "conversationId") @Valid @NotBlank String conversationId,
            @RequestParam(name = "lastN", defaultValue = "10") int lastN) {
        if (lastN > maxLastN) {
            return ResponseEntity.badRequest().build();
        }
        var messages = chatMemoryPort.get(new ConversationId(conversationId), lastN);
        if (messages.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(
                new ConversationHistoryDto(
                        conversationId,
                        messages.stream()
                                .map(ConversationHistoryDto.ChatMessageDto::fromDomain)
                                .toList()));
    }

    record ConversationHistoryDto(String conversationId, List<ChatMessageDto> messages) {
        enum ChatMessageTypeDto {
            USER,
            ASSISTANT;

            static ChatMessageTypeDto fromDomain(ChatMessageType type) {
                return switch (type) {
                    case ChatMessageType.USER -> ChatMessageTypeDto.USER;
                    case ChatMessageType.ASSISTANT -> ChatMessageTypeDto.ASSISTANT;
                };
            }
        }

        record ChatMessageDto(String content, ChatMessageTypeDto type) {
            static ChatMessageDto fromDomain(ChatMessage message) {
                return new ChatMessageDto(
                        message.content(),
                        ChatMessageTypeDto.fromDomain(message.type()));
            }
        }
    }
}
