package io.github.kszapsza.springairag.adapter.db.realestate;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface RealEstateRepository extends JpaRepository<RealEstateEntity, Long> {

  @Query("""
          SELECT r FROM RealEstateEntity r
          WHERE (:location IS NULL OR LOWER(r.location) LIKE LOWER(CONCAT('%', :location, '%')))
            AND (:minPrice IS NULL OR r.price >= :minPrice)
            AND (:maxPrice IS NULL OR r.price <= :maxPrice)
            AND (:minBedrooms IS NULL OR r.bedrooms >= :minBedrooms)
            AND r.isActive = true
      """)
  List<RealEstateEntity> searchRealEstate(
      @Param("location") String location,
      @Param("minPrice") BigDecimal minPrice,
      @Param("maxPrice") BigDecimal maxPrice,
      @Param("minBedrooms") Integer minBedrooms);
}
