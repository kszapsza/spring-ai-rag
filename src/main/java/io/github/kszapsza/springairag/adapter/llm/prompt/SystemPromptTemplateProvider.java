package io.github.kszapsza.springairag.adapter.llm.prompt;

import org.springframework.ai.chat.prompt.SystemPromptTemplate;

public interface SystemPromptTemplateProvider {
    SystemPromptTemplate getSystemPromptTemplate();
}
