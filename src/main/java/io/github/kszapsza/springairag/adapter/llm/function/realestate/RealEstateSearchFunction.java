package io.github.kszapsza.springairag.adapter.llm.function.realestate;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.kszapsza.springairag.adapter.db.realestate.RealEstateEntity;
import io.github.kszapsza.springairag.adapter.db.realestate.RealEstateRepository;

public class RealEstateSearchFunction
        implements Function<RealEstateSearchFunction.Request, RealEstateSearchFunction.Response> {

    private static final Logger logger = LoggerFactory.getLogger(RealEstateSearchFunction.class);

    private final RealEstateRepository realEstateRepository;

    public RealEstateSearchFunction(RealEstateRepository realEstateRepository) {
        this.realEstateRepository = realEstateRepository;
    }

    @Override
    public Response apply(Request request) {
        logger.info("Searching for real estate properties. Request: {}", request.toString());
        try {
            return new Response(
                    realEstateRepository.searchRealEstate(
                            request.location(),
                            request.minPrice(),
                            request.maxPrice(),
                            request.minBedrooms()));
        } catch (Exception e) {
            logger.error("An error occured when searching for real estate properties", e);
            return new Response(List.of());
        }
    }

    public record Request(
            String location,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Integer minBedrooms) {
    }

    public record Response(List<RealEstateEntity> results) {
    }
}
