package io.github.kszapsza.springairag.adapter.llm.openai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import io.github.kszapsza.springairag.adapter.application.SystemMessageProperties;
import io.github.kszapsza.springairag.domain.chat.ChatProvider;
import io.github.kszapsza.springairag.domain.chat.ChatRequest;
import io.github.kszapsza.springairag.domain.chat.ChatResponse;

@Component
public class OpenAiChatProvider implements ChatProvider {
    private static final Logger logger = LoggerFactory.getLogger(OpenAiChatProvider.class);

    private final OpenAiChatModel chatModel;
    private final SystemMessageProperties systemMessageProperties;
    private final SystemPromptTemplate systemPromptTemplate;

    public OpenAiChatProvider(
            OpenAiChatModel chatModel,
            SystemMessageProperties systemMessageProperties,
            @Value("classpath:/chat/system-message.txt") Resource systemPromptResource) {
        this.chatModel = chatModel;
        this.systemMessageProperties = systemMessageProperties;
        this.systemPromptTemplate = loadSystemPromptTemplate(systemPromptResource);
    }

    private SystemPromptTemplate loadSystemPromptTemplate(Resource systemPromptResource) {
        if (systemPromptResource == null || !systemPromptResource.exists() || !systemPromptResource.isReadable()) {
            throw new IllegalStateException("System prompt resource is missing or not readable");
        }
        return new SystemPromptTemplate(systemPromptResource);
    }

    @Override
    public ChatResponse chat(ChatRequest request) {
        if (request.message() == null || request.message().trim().isEmpty()) {
            logger.warn("Received a null or empty request message");
            return new ChatResponse.Failure("Request message cannot be null or empty");
        }
        try {
            var content = callChatModel(request.message()).getContent();
            return new ChatResponse.Success(content);
        } catch (Exception ex) {
            return new ChatResponse.Failure(ex.getMessage());
        }
    }

    private Message callChatModel(String userMessageContent) {
        return ChatClient.create(chatModel)
                .prompt()
                .advisors(new SimpleLoggerAdvisor())
                .system(systemPromptTemplate.createMessage(systemMessageProperties.placeholders()).getContent())
                .user(userMessageContent)
                .call()
                .chatResponse()
                .getResult()
                .getOutput();
    }
}
