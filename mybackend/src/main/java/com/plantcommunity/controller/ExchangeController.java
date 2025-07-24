package com.plantcommunity.controller;

import com.plantcommunity.dto.exchange.ExchangeItemRequest;
import com.plantcommunity.dto.exchange.ExchangeItemResponse;
import com.plantcommunity.entity.ExchangeItem;
import com.plantcommunity.service.ExchangeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/exchange")
public class ExchangeController {
    
    private final ExchangeService exchangeService;
    
    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }
    
    @GetMapping
    public ResponseEntity<Page<ExchangeItemResponse>> getExchangeItems(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Integer priceMin,
            @RequestParam(required = false) Integer priceMax,
            @RequestParam(required = false) String condition,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "latest") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication != null ? authentication.getName() : null;
        
        Page<ExchangeItemResponse> items = exchangeService.getExchangeItems(
            category, location, priceMin, priceMax, condition, search, sort, page, size, currentUserEmail);
        
        return ResponseEntity.ok(items);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ExchangeItemResponse> getExchangeItem(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication != null ? authentication.getName() : null;
        
        ExchangeItemResponse item = exchangeService.getExchangeItem(id, currentUserEmail);
        return ResponseEntity.ok(item);
    }
    
    @PostMapping
    public ResponseEntity<Map<String, Object>> createExchangeItem(@Valid @RequestBody ExchangeItemRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        
        ExchangeItem item = exchangeService.createExchangeItem(request, userEmail);
        
        Map<String, Object> response = new HashMap<>();
        response.put("id", item.getId());
        response.put("message", "상품이 등록되었습니다");
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/{id}/favorite")
    public ResponseEntity<Map<String, Object>> toggleExchangeFavorite(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        
        Map<String, Object> response = exchangeService.toggleExchangeFavorite(id, userEmail);
        return ResponseEntity.ok(response);
    }
}
