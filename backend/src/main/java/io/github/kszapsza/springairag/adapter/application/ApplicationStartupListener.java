package io.github.kszapsza.springairag.adapter.application;

import io.github.kszapsza.springairag.domain.embedding.EmbeddingsFeeder;
import io.github.kszapsza.springairag.domain.realestate.RealEstateFeeder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("local")
class ApplicationStartupListener {

    private final EmbeddingsFeeder embeddingsFeeder;
    private final RealEstateFeeder realEstateFeeder;

    ApplicationStartupListener(
            EmbeddingsFeeder embeddingsFeeder,
            RealEstateFeeder realEstateFeeder) {
        this.embeddingsFeeder = embeddingsFeeder;
        this.realEstateFeeder = realEstateFeeder;
    }

    @EventListener(ApplicationReadyEvent.class)
    void onApplicationReady() {
        embeddingsFeeder.importEmbeddings();
        realEstateFeeder.feedDummyData();
    }
}
