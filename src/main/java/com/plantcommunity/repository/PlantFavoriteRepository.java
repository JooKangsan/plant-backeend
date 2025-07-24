package com.plantcommunity.repository;

import com.plantcommunity.entity.PlantFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlantFavoriteRepository extends JpaRepository<PlantFavorite, Long> {
    Optional<PlantFavorite> findByUserIdAndPlantId(Long userId, Long plantId);
    boolean existsByUserIdAndPlantId(Long userId, Long plantId);
}
