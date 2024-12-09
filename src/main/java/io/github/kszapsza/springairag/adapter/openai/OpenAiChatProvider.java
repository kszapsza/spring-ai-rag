package io.github.kszapsza.springairag.adapter.openai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.retry.NonTransientAiException;
import org.springframework.stereotype.Component;

import io.github.kszapsza.springairag.domain.chat.ChatProvider;
import io.github.kszapsza.springairag.domain.chat.ChatRequest;
import io.github.kszapsza.springairag.domain.chat.ChatResponse;

@Component
public class OpenAiChatProvider implements ChatProvider {
    private static final Logger logger = LoggerFactory.getLogger(ChatProvider.class);

    private final OpenAiChatModel chatModel;

    public OpenAiChatProvider(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    public ChatResponse chat(ChatRequest request) {
        var model = chatModel.getDefaultOptions().getModel();
        var temperature = chatModel.getDefaultOptions().getTemperature();

        try {
            logger.info("Calling OpenAI model: {} (temperature: {}) with user message: {}",
                    model, temperature, request.message());
            var content = callChatModel(request.message()).getContent();
            return new ChatResponse.Success(content);
        } catch (NonTransientAiException ex) {
            logger.error("Could not generate a response using OpenAI model: {}. Error message: {}",
                    model, ex.getMessage(), ex);
            return new ChatResponse.Failure(ex.getMessage());
        }
    }

    private Message callChatModel(String textContent) {
        var userMessage = new UserMessage(textContent);
        var prompt = new Prompt(userMessage);

        return chatModel.call(prompt)
                .getResult()
                .getOutput();
    }
}
