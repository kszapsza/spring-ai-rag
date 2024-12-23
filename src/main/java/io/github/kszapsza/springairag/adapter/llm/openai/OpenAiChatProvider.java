package io.github.kszapsza.springairag.adapter.llm.openai;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.context.annotation.Configuration;

import io.github.kszapsza.springairag.domain.chat.ChatProvider;
import io.github.kszapsza.springairag.domain.chat.ChatRequest;
import io.github.kszapsza.springairag.domain.chat.ChatResponse;

@Configuration
public class OpenAiChatProvider implements ChatProvider {

    private static final Logger logger = LoggerFactory.getLogger(OpenAiChatProvider.class);

    private final ChatClient chatClient;
    private final ChatOptions chatOptions;
    private final List<Advisor> advisors;
    private final Message systemMessage;

    public OpenAiChatProvider(
            ChatClient chatClient,
            ChatOptions chatOptions,
            List<Advisor> advisors,
            Message systemMessage) {
        this.chatClient = chatClient;
        this.chatOptions = chatOptions;
        this.advisors = advisors;
        this.systemMessage = systemMessage;
    }

    @Override
    public ChatResponse chat(ChatRequest request) {
        try {
            return callChatModel(request.message())
                    .map((generation) -> generation.getOutput().getContent())
                    .map((content) -> (ChatResponse) new ChatResponse.Success(content))
                    .orElseGet(() -> {
                        logger.warn("Received null generation result");
                        return new ChatResponse.Error.ServerError("Received null generation result");
                    });
        } catch (Exception ex) {
            logger.error("Error during chat model call", ex);
            return new ChatResponse.Error.ServerError("An internal error occurred during processing.");
        }
    }

    private Optional<Generation> callChatModel(String userMessageContent) {
        return Optional.ofNullable(
                chatClient.prompt()
                        .options(chatOptions)
                        .advisors(advisors)
                        .system(systemMessage.getContent())
                        .user(userMessageContent)
                        .call()
                        .chatResponse()
                        .getResult());
    }
}
