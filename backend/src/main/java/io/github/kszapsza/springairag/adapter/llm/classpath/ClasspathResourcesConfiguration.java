package io.github.kszapsza.springairag.adapter.llm.classpath;

import io.github.kszapsza.springairag.adapter.llm.EmbeddingDocumentsProvider;
import io.github.kszapsza.springairag.adapter.llm.SystemPromptTemplateProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
class ClasspathResourcesConfiguration {

    @Bean
    EmbeddingDocumentsProvider embeddingDocumentsProvider(
            @Value("${app.chat.embedding.document}") Resource documentResource) {
        return new ClasspathEmbeddingDocumentsProvider(documentResource);
    }

    @Bean
    SystemPromptTemplateProvider systemPromptTemplateProvider(
            @Value("${app.chat.system-prompt.resource}") Resource systemPromptResource) {
        return new ClasspathSystemPromptTemplateProvider(systemPromptResource);
    }
}
