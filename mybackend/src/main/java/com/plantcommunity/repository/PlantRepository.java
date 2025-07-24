package com.plantcommunity.repository;

import com.plantcommunity.entity.Plant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {
    
    @Query("SELECT p FROM Plant p WHERE " +
           "(:category IS NULL OR p.category = :category) AND " +
           "(:difficulty IS NULL OR p.difficulty = :difficulty) AND " +
           "(:search IS NULL OR p.name LIKE %:search% OR p.scientificName LIKE %:search%)")
    Page<Plant> findPlantsWithFilters(
        @Param("category") Plant.PlantCategory category,
        @Param("difficulty") Plant.PlantDifficulty difficulty,
        @Param("search") String search,
        Pageable pageable
    );
}
