package io.github.kszapsza.springairag.adapter.llm.data;

import java.util.List;

import org.springframework.ai.document.Document;

public interface EmbeddingDataProvider {
    List<Document> provideData();
}
