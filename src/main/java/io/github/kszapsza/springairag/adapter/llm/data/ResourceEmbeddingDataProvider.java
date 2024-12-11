package io.github.kszapsza.springairag.adapter.llm.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class ResourceEmbeddingDataProvider implements EmbeddingDataProvider {

    private static final Logger logger = LoggerFactory.getLogger(ResourceEmbeddingDataProvider.class);

    private final List<Document> data;

    public ResourceEmbeddingDataProvider(
            @Value("classpath:/embedding/example-document.txt") Resource exampleDocumentResource) {
        if (exampleDocumentResource == null || !exampleDocumentResource.exists()
                || !exampleDocumentResource.isReadable()) {
            throw new IllegalStateException("RAG input data is missing or not readable");
        }
        this.data = new TextReader(exampleDocumentResource).read();
        logger.info("Successfully loaded RAG input data from resource: {}", exampleDocumentResource);
    }

    @Override
    public List<Document> provideData() {
        return data;
    }

}
