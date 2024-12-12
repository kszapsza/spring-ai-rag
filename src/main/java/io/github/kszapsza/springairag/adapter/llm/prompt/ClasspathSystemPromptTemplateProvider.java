package io.github.kszapsza.springairag.adapter.llm.prompt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class ClasspathSystemPromptTemplateProvider implements SystemPromptTemplateProvider {

    private static final Logger logger = LoggerFactory.getLogger(ClasspathSystemPromptTemplateProvider.class);

    private final SystemPromptTemplate systemPromptTemplate;

    public ClasspathSystemPromptTemplateProvider(
            @Value("classpath:/chat/system-message.txt") Resource systemPromptResource) {
        if (systemPromptResource == null || !systemPromptResource.exists() || !systemPromptResource.isReadable()) {
            throw new IllegalStateException("System prompt resource is missing or not readable");
        }
        this.systemPromptTemplate = new SystemPromptTemplate(systemPromptResource);
        logger.info("Successfully loaded SystemPromptTemplate from resource: {}", systemPromptResource);
    }

    @Override
    public SystemPromptTemplate getSystemPromptTemplate() {
        return systemPromptTemplate;
    }
}
