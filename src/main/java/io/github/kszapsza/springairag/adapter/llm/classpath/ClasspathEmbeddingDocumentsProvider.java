package io.github.kszapsza.springairag.adapter.llm.classpath;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.JsonReader;
import org.springframework.core.io.Resource;

import io.github.kszapsza.springairag.adapter.llm.EmbeddingDocumentsProvider;

public class ClasspathEmbeddingDocumentsProvider implements EmbeddingDocumentsProvider {

    private static final Logger logger = LoggerFactory.getLogger(ClasspathEmbeddingDocumentsProvider.class);

    private final List<Document> data;

    public ClasspathEmbeddingDocumentsProvider(Resource documentsResource) {
        if (documentsResource == null || !documentsResource.exists() || !documentsResource.isReadable()) {
            throw new IllegalArgumentException("RAG input data is missing or not readable");
        }
        var reader = new JsonReader(documentsResource, "question", "answer", "category");
        this.data = reader.read();
        logger.info("Loaded {} documents from resource: {}", data.size(), documentsResource);
    }

    @Override
    public List<Document> provideData() {
        return data;
    }

}
