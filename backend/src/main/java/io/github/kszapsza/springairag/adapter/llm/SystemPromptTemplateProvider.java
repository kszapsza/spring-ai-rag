package io.github.kszapsza.springairag.adapter.llm;

import org.springframework.ai.chat.prompt.SystemPromptTemplate;

public interface SystemPromptTemplateProvider {
    SystemPromptTemplate getSystemPromptTemplate();
}
