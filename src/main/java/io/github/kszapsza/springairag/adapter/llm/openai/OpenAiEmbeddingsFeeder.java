package io.github.kszapsza.springairag.adapter.llm.openai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import io.github.kszapsza.springairag.adapter.llm.data.EmbeddingDataProvider;
import io.github.kszapsza.springairag.domain.embedding.EmbeddingsFeeder;

@Component
public class OpenAiEmbeddingsFeeder implements EmbeddingsFeeder {

    private static final Logger logger = LoggerFactory.getLogger(OpenAiEmbeddingsFeeder.class);

    private final EmbeddingDataProvider dataProvider;
    private final VectorStore vectorStore;

    public OpenAiEmbeddingsFeeder(
            VectorStore vectorStore,
            EmbeddingDataProvider dataProvider) {
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
