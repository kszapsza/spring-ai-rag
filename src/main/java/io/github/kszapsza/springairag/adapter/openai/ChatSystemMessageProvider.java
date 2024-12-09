package io.github.kszapsza.springairag.adapter.openai;

import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class ChatSystemMessageProvider {
    private static final Logger logger = LoggerFactory.getLogger(ChatSystemMessageProvider.class);
    private static final String SYSTEM_MESSAGE_PATH = "chat/system-message.txt";

    private final SystemMessage systemMessage;

    public ChatSystemMessageProvider() {
        this.systemMessage = new SystemMessage(loadSystemMessage().trim());
        logger.info("Successfully loaded system message from path: {}. Content: {}",
                SYSTEM_MESSAGE_PATH, systemMessage.getContent());
    }

    public SystemMessage systemMessage() {
        return systemMessage;
    }

    private static String loadSystemMessage() {
        try {
            var path = new ClassPathResource(SYSTEM_MESSAGE_PATH).getFile().toPath();
            return Files.readString(path);
        } catch (Exception ex) {
            throw new IllegalStateException(
                    String.format("Failed to load chatbot system message. Path: {}", SYSTEM_MESSAGE_PATH), ex);
        }
    }
}
