package io.github.kszapsza.springairag.adapter.llm;

import org.springframework.ai.document.Document;

import java.util.List;

public interface EmbeddingDocumentsProvider {
    List<Document> provideData();
}
