package io.github.kszapsza.springairag.adapter.db.realestate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "real_estate", indexes = {
        @Index(name = "idx_location", columnList = "location"),
        @Index(name = "idx_country_code", columnList = "country_code"),
        @Index(name = "idx_price", columnList = "price"),
        @Index(name = "idx_bedrooms", columnList = "bedrooms"),
        @Index(name = "idx_is_active", columnList = "is_active")
})
public class RealEstateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 2)
    private String countryCode;

    @Column(nullable = false, length = 255)
    private String location;

    @Column(length = 255)
    private String address;

    @Column(nullable = false)
    private Integer bedrooms;

    @Column(nullable = false)
    private Integer bathrooms;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(precision = 10, scale = 2)
    private BigDecimal size;

    @Column(nullable = false)
    private Boolean hasBalcony = false;

    @Column(nullable = false)
    private Boolean hasTerrace = false;

    @Column(nullable = false)
    private Boolean hasGarden = false;

    @Column(nullable = false)
    private Boolean energyEfficient = false;

    @Column(nullable = true)
    private Integer greenSpaceDistance;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public RealEstateEntity() {
    }

    public RealEstateEntity(Long id, String title, String description, String countryCode, String location,
            String address, Integer bedrooms, Integer bathrooms, BigDecimal price, String currency,
            BigDecimal size, Boolean hasBalcony, Boolean hasTerrace, Boolean hasGarden,
            Boolean energyEfficient, Integer greenSpaceDistance, Boolean isActive,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.countryCode = countryCode;
        this.location = location;
        this.address = address;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.price = price;
        this.currency = currency;
        this.size = size;
        this.hasBalcony = hasBalcony;
        this.hasTerrace = hasTerrace;
        this.hasGarden = hasGarden;
        this.energyEfficient = energyEfficient;
        this.greenSpaceDistance = greenSpaceDistance;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
    }

    public Integer getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }

    public Boolean getHasBalcony() {
        return hasBalcony;
    }

    public void setHasBalcony(Boolean hasBalcony) {
        this.hasBalcony = hasBalcony;
    }

    public Boolean getHasTerrace() {
        return hasTerrace;
    }

    public void setHasTerrace(Boolean hasTerrace) {
        this.hasTerrace = hasTerrace;
    }

    public Boolean getHasGarden() {
        return hasGarden;
    }

    public void setHasGarden(Boolean hasGarden) {
        this.hasGarden = hasGarden;
    }

    public Boolean getEnergyEfficient() {
        return energyEfficient;
    }

    public void setEnergyEfficient(Boolean energyEfficient) {
        this.energyEfficient = energyEfficient;
    }

    public Integer getGreenSpaceDistance() {
        return greenSpaceDistance;
    }

    public void setGreenSpaceDistance(Integer greenSpaceDistance) {
        this.greenSpaceDistance = greenSpaceDistance;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
