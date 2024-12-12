package io.github.kszapsza.springairag.adapter.llm.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.JsonReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class ClasspathEmbeddingDataProvider implements EmbeddingDataProvider {

    private static final Logger logger = LoggerFactory.getLogger(ClasspathEmbeddingDataProvider.class);

    private final List<Document> data;

    public ClasspathEmbeddingDataProvider(
            @Value("classpath:/embedding/faq-data.json") Resource exampleDocumentResource) {
        if (exampleDocumentResource == null || !exampleDocumentResource.exists()
                || !exampleDocumentResource.isReadable()) {
            throw new IllegalStateException("RAG input data is missing or not readable");
        }
        var reader = new JsonReader(exampleDocumentResource, "question", "answer", "category");
        this.data = reader.read();
        logger.info("Successfully loaded RAG input data from resource: {}", exampleDocumentResource);
    }

    @Override
    public List<Document> provideData() {
        return data;
    }

}
