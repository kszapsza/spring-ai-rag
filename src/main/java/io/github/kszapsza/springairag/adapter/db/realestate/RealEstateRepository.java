package io.github.kszapsza.springairag.adapter.db.realestate;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface RealEstateRepository extends JpaRepository<RealEstateEntity, Long> {

  @Query("""
          SELECT r FROM RealEstateEntity r
          WHERE (LOWER(r.location) LIKE LOWER(CONCAT('%', :location, '%')) OR :location IS NULL)
            AND (r.countryCode = :countryCode OR :countryCode IS NULL)
            AND (r.price >= :minPrice OR :minPrice IS NULL)
            AND (r.price <= :maxPrice OR :maxPrice IS NULL)
            AND (r.bedrooms >= :minBedrooms OR :minBedrooms IS NULL)
            AND r.isActive = true
      """)
  List<RealEstateEntity> searchRealEstate(
      @Param("countryCode") String countryCode,
      @Param("location") String location,
      @Param("minPrice") BigDecimal minPrice,
      @Param("maxPrice") BigDecimal maxPrice,
      @Param("minBedrooms") Integer minBedrooms);
}
