package io.github.kszapsza.springairag.adapter.llm.function;

import io.github.kszapsza.springairag.adapter.db.realestate.RealEstateRepository;
import io.github.kszapsza.springairag.adapter.llm.function.realestate.RealEstateSearchFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
class FunctionConfiguration {

    @Bean
    @Description("Searches real estate listings by location, price range, bedrooms, and active status")
    Function<RealEstateSearchFunction.Request, RealEstateSearchFunction.Response> realEstateSearchFunction(
            RealEstateRepository realEstateRepository) {
        return new RealEstateSearchFunction(realEstateRepository);
    }
}
