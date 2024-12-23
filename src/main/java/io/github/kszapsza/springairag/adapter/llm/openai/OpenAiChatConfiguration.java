package io.github.kszapsza.springairag.adapter.llm.openai;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.kszapsza.springairag.adapter.llm.ChatProperties;
import io.github.kszapsza.springairag.adapter.llm.SystemPromptTemplateProvider;

@Configuration
class OpenAiChatConfiguration {

    @Bean
    ChatClient chatClient(OpenAiChatModel chatModel) {
        return ChatClient.create(chatModel);
    }

    @Bean
    ChatOptions chatOptions() {
        return OpenAiChatOptions.builder()
                .withFunction("realEstateSearchFunction")
                .build();
    }

    @Bean
    List<Advisor> chatAdvisors(VectorStore vectorStore) {
        return List.of(
                new SimpleLoggerAdvisor(),
                new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()));
    }

    @Bean
    Message systemMessage(
            SystemPromptTemplateProvider systemPromptTemplateProvider,
            ChatProperties chatProperties) {
        return systemPromptTemplateProvider.getSystemPromptTemplate()
                .createMessage(chatProperties.systemPrompt().placeholders());
    }
}
