package io.github.kszapsza.springairag.adapter.db.realestate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.kszapsza.springairag.domain.realestate.RealEstateFeeder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class RealEstateRepositoryFeeder implements RealEstateFeeder {

    private static final Logger logger = LoggerFactory.getLogger(RealEstateRepositoryFeeder.class);

    private final RealEstateRepository realEstateRepository;
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    public RealEstateRepositoryFeeder(
            RealEstateRepository realEstateRepository,
            ResourceLoader resourceLoader,
            ObjectMapper objectMapper) {
        this.realEstateRepository = realEstateRepository;
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
    }

    @Override
    public void feedDummyData() {
        deleteExistingDummyData();
        loadAndFeedDummyData();
    }

    private void deleteExistingDummyData() {
        logger.info("Deleting all real estate data from the local DB...");
        realEstateRepository.deleteAll();
        logger.info("Deleting all real estate data from the local DB completed.");
    }

    private void loadAndFeedDummyData() {
        logger.info("Feeding the local DB with dummy real estate data...");
        var resource = resourceLoader.getResource("classpath:/db/dummy-real-estate.json");
        try {
            var dummyProperties = objectMapper.readValue(resource.getInputStream(),
                    new TypeReference<List<RealEstateEntity>>() {
                    });
            var insertedRecords = realEstateRepository.saveAll(dummyProperties);
            logger.info("Inserted {} dummy real estate DB records to the local DB.", insertedRecords.size());
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load dummy real estate data", ex);
        }
    }
}
