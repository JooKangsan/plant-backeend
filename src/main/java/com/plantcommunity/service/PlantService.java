package com.plantcommunity.service;

import com.plantcommunity.entity.Plant;
import com.plantcommunity.entity.PlantFavorite;
import com.plantcommunity.entity.User;
import com.plantcommunity.repository.PlantFavoriteRepository;
import com.plantcommunity.repository.PlantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class PlantService {
    
    private final PlantRepository plantRepository;
    private final PlantFavoriteRepository plantFavoriteRepository;
    private final UserService userService;
    
    public PlantService(PlantRepository plantRepository, PlantFavoriteRepository plantFavoriteRepository,
                       UserService userService) {
        this.plantRepository = plantRepository;
        this.plantFavoriteRepository = plantFavoriteRepository;
        this.userService = userService;
    }
    
    public Page<Plant> getPlants(String category, String difficulty, String search,
                                String sort, int page, int size) {
        
        Plant.PlantCategory plantCategory = null;
        if (category != null) {
            plantCategory = Plant.PlantCategory.valueOf(category.toUpperCase());
        }
        
        Plant.PlantDifficulty plantDifficulty = null;
        if (difficulty != null) {
            plantDifficulty = Plant.PlantDifficulty.valueOf(difficulty.toUpperCase());
        }
        
        Sort sortBy = switch (sort != null ? sort : "name") {
            case "difficulty" -> Sort.by(Sort.Direction.ASC, "difficulty");
            case "popularity" -> Sort.by(Sort.Direction.DESC, "rating");
            default -> Sort.by(Sort.Direction.ASC, "name");
        };
        
        Pageable pageable = PageRequest.of(page, size, sortBy);
        return plantRepository.findPlantsWithFilters(plantCategory, plantDifficulty, search, pageable);
    }
    
    public Plant getPlant(Long id) {
        return plantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("식물을 찾을 수 없습니다"));
    }
    
    public Map<String, Object> togglePlantFavorite(Long plantId, String userEmail) {
        User user = userService.findByEmail(userEmail);
        Plant plant = getPlant(plantId);
        
        boolean isFavorite;
        String message;
        
        var existingFavorite = plantFavoriteRepository.findByUserIdAndPlantId(user.getId(), plantId);
        if (existingFavorite.isPresent()) {
            plantFavoriteRepository.delete(existingFavorite.get());
            isFavorite = false;
            message = "식물이 즐겨찾기에서 제거되었습니다";
        } else {
            plantFavoriteRepository.save(new PlantFavorite(user, plant));
            isFavorite = true;
            message = "식물이 즐겨찾기에 추가되었습니다";
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("isFavorite", isFavorite);
        response.put("message", message);
        
        return response;
    }
}
