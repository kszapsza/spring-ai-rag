package io.github.kszapsza.springairag.adapter.llm.openai;

import io.github.kszapsza.springairag.adapter.llm.EmbeddingDocumentsProvider;
import io.github.kszapsza.springairag.domain.embedding.EmbeddingsFeeder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

@Component
class OpenAiEmbeddingsFeeder implements EmbeddingsFeeder {

    private static final Logger logger = LoggerFactory.getLogger(OpenAiEmbeddingsFeeder.class);

    private final EmbeddingDocumentsProvider dataProvider;
    private final VectorStore vectorStore;

    OpenAiEmbeddingsFeeder(
            VectorStore vectorStore,
            EmbeddingDocumentsProvider dataProvider) {
        this.vectorStore = vectorStore;
        this.dataProvider = dataProvider;
    }

    @Override
    public void importEmbeddings() {
        try {
            var documents = dataProvider.provideData();
            vectorStore.accept(documents);
            logger.info("{} documents imported to the vector store", documents.size());
        } catch (Exception e) {
            logger.error("Could not embed documents into the vector store", e);
            throw e;
        }
    }
}
