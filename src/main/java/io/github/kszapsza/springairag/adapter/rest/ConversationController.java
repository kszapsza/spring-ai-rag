package io.github.kszapsza.springairag.adapter.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.kszapsza.springairag.domain.chat.ChatMessage;
import io.github.kszapsza.springairag.domain.chat.ChatMessageType;
import io.github.kszapsza.springairag.domain.chat.memory.ChatMemoryPort;
import io.github.kszapsza.springairag.domain.chat.memory.ConversationId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/conversation")
public class ConversationController {

    private final ChatMemoryPort chatMemoryPort;
    private final int maxLastN;

    public ConversationController(
            ChatMemoryPort chatMemoryPort,
            @Value("${app.conversation.max-last-n}") int maxLastN) {
        this.chatMemoryPort = chatMemoryPort;
        this.maxLastN = maxLastN;
    }

    @GetMapping("{conversationId}")
    public ResponseEntity<ConversationHistoryDto> getConversationHistory(
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
                                .map((message) -> new ConversationHistoryDto.ChatMessageDto(
                                        message.content(),
                                        ConversationHistoryDto.ChatMessageTypeDto.valueOf(message.type().name())))
                                .toList()));
    }

    record ConversationHistoryDto(String conversationId, List<ChatMessageDto> messages) {
        record ChatMessageDto(String content, ChatMessageTypeDto type) {
            static ChatMessageDto fromDomain(ChatMessage message) {
                return new ChatMessageDto(
                        message.content(),
                        ChatMessageTypeDto.fromDomain(message.type()));
            }
        }

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
    }
}
