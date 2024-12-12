package io.github.kszapsza.springairag.adapter.llm;

import java.util.List;

import org.springframework.ai.document.Document;

public interface EmbeddingDocumentsProvider {
    List<Document> provideData();
}
