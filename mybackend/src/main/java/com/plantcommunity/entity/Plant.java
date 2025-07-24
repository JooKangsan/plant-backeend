package com.plantcommunity.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "plants")
@EntityListeners(AuditingEntityListener.class)
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String scientificName;

    @Enumerated(EnumType.STRING)
    private PlantCategory category;

    @ElementCollection
    @CollectionTable(name = "plant_images", joinColumns = @JoinColumn(name = "plant_id"))
    @Column(name = "image_url")
    private List<String> images;

    @Enumerated(EnumType.STRING)
    private PlantDifficulty difficulty;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Embedded
    private PlantCare care;

    @ElementCollection
    @CollectionTable(name = "plant_tips", joinColumns = @JoinColumn(name = "plant_id"))
    @Column(name = "tip")
    private List<String> tips;

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL)
    private List<PlantProblem> commonProblems;

    private double rating = 0.0;
    private int reviewsCount = 0;
    private boolean isPopular = false;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public enum PlantCategory {
        FOLIAGE, SUCCULENT, HERB, FLOWER, VEGETABLE
    }

    public enum PlantDifficulty {
        VERY_EASY, EASY, NORMAL, HARD
    }

    @Embeddable
    public static class PlantCare {
        private String light;
        private String lightDetail;
        private String water;
        private String waterDetail;
        private String temperature;
        private String temperatureDetail;
        private String humidity;
        private String humidityDetail;

        // Constructors
        public PlantCare() {}

        // Getters and Setters
        public String getLight() { return light; }
        public void setLight(String light) { this.light = light; }

        public String getLightDetail() { return lightDetail; }
        public void setLightDetail(String lightDetail) { this.lightDetail = lightDetail; }

        public String getWater() { return water; }
        public void setWater(String water) { this.water = water; }

        public String getWaterDetail() { return waterDetail; }
        public void setWaterDetail(String waterDetail) { this.waterDetail = waterDetail; }

        public String getTemperature() { return temperature; }
        public void setTemperature(String temperature) { this.temperature = temperature; }

        public String getTemperatureDetail() { return temperatureDetail; }
        public void setTemperatureDetail(String temperatureDetail) { this.temperatureDetail = temperatureDetail; }

        public String getHumidity() { return humidity; }
        public void setHumidity(String humidity) { this.humidity = humidity; }

        public String getHumidityDetail() { return humidityDetail; }
        public void setHumidityDetail(String humidityDetail) { this.humidityDetail = humidityDetail; }
    }

    // Constructors
    public Plant() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getScientificName() { return scientificName; }
    public void setScientificName(String scientificName) { this.scientificName = scientificName; }

    public PlantCategory getCategory() { return category; }
    public void setCategory(PlantCategory category) { this.category = category; }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }

    public PlantDifficulty getDifficulty() { return difficulty; }
    public void setDifficulty(PlantDifficulty difficulty) { this.difficulty = difficulty; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public PlantCare getCare() { return care; }
    public void setCare(PlantCare care) { this.care = care; }

    public List<String> getTips() { return tips; }
    public void setTips(List<String> tips) { this.tips = tips; }

    public List<PlantProblem> getCommonProblems() { return commonProblems; }
    public void setCommonProblems(List<PlantProblem> commonProblems) { this.commonProblems = commonProblems; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public int getReviewsCount() { return reviewsCount; }
    public void setReviewsCount(int reviewsCount) { this.reviewsCount = reviewsCount; }

    public boolean isPopular() { return isPopular; }
    public void setPopular(boolean popular) { isPopular = popular; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
