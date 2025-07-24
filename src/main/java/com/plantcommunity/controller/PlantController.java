package com.plantcommunity.controller;

import com.plantcommunity.entity.Plant;
import com.plantcommunity.service.PlantService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/plants")
public class PlantController {
    
    private final PlantService plantService;
    
    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }
    
    @GetMapping
    public ResponseEntity<Page<Plant>> getPlants(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String difficulty,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Page<Plant> plants = plantService.getPlants(category, difficulty, search, sort, page, size);
        return ResponseEntity.ok(plants);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Plant> getPlant(@PathVariable Long id) {
        Plant plant = plantService.getPlant(id);
        return ResponseEntity.ok(plant);
    }
    
    @PostMapping("/{id}/favorite")
    public ResponseEntity<Map<String, Object>> togglePlantFavorite(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        
        Map<String, Object> response = plantService.togglePlantFavorite(id, userEmail);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/identify")
    public ResponseEntity<Map<String, Object>> identifyPlant() {
        // TODO: AI 식물 식별 기능 구현
        throw new RuntimeException("AI 식물 식별 기능은 아직 구현되지 않았습니다");
    }
}
