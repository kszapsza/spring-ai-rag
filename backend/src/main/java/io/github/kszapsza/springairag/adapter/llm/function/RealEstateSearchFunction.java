package io.github.kszapsza.springairag.adapter.llm.function;

import io.github.kszapsza.springairag.adapter.db.realestate.RealEstateEntity;
import io.github.kszapsza.springairag.adapter.db.realestate.RealEstateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

class RealEstateSearchFunction
        implements Function<RealEstateSearchFunction.Request, RealEstateSearchFunction.Response> {

    private static final Logger logger = LoggerFactory.getLogger(RealEstateSearchFunction.class);

    private final RealEstateRepository realEstateRepository;

    RealEstateSearchFunction(RealEstateRepository realEstateRepository) {
        this.realEstateRepository = realEstateRepository;
    }

    @Override
    public Response apply(Request request) {
        logger.info("Searching for real estate properties. Request: {}", request.toString());
        try {
            return new Response(
                    realEstateRepository.searchRealEstate(
                            request.countryCode(),
                            request.location(),
                            request.minPrice(),
                            request.maxPrice(),
                            request.minBedrooms()));
        } catch (Exception e) {
            logger.error("An error occurred when searching for real estate properties", e);
            return new Response(List.of());
        }
    }

    record Request(
            String countryCode,
            String location,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Integer minBedrooms) {
    }

    record Response(List<RealEstateEntity> results) {
    }
}
