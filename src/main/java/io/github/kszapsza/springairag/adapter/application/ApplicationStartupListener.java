package io.github.kszapsza.springairag.adapter.application;

import io.github.kszapsza.springairag.domain.embedding.EmbeddingsFeeder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("local")
public class ApplicationStartupListener {

    private final EmbeddingsFeeder embeddingsFeeder;

    public ApplicationStartupListener(EmbeddingsFeeder embeddingsFeeder) {
        this.embeddingsFeeder = embeddingsFeeder;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        embeddingsFeeder.importEmbeddings();
    }
}
