package com.plantcommunity.service;

import com.plantcommunity.dto.exchange.ExchangeItemRequest;
import com.plantcommunity.dto.exchange.ExchangeItemResponse;
import com.plantcommunity.entity.ExchangeFavorite;
import com.plantcommunity.entity.ExchangeItem;
import com.plantcommunity.entity.User;
import com.plantcommunity.repository.ExchangeFavoriteRepository;
import com.plantcommunity.repository.ExchangeItemRepository;
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
public class ExchangeService {
    
    private final ExchangeItemRepository exchangeItemRepository;
    private final ExchangeFavoriteRepository exchangeFavoriteRepository;
    private final UserService userService;
    
    public ExchangeService(ExchangeItemRepository exchangeItemRepository, 
                          ExchangeFavoriteRepository exchangeFavoriteRepository,
                          UserService userService) {
        this.exchangeItemRepository = exchangeItemRepository;
        this.exchangeFavoriteRepository = exchangeFavoriteRepository;
        this.userService = userService;
    }
    
    public Page<ExchangeItemResponse> getExchangeItems(String category, String location, 
                                                      Integer priceMin, Integer priceMax,
                                                      String condition, String search, String sort,
                                                      int page, int size, String currentUserEmail) {
        
        ExchangeItem.ExchangeCategory exchangeCategory = null;
        if (category != null && !category.equals("all")) {
            exchangeCategory = ExchangeItem.ExchangeCategory.valueOf(category.toUpperCase());
        }
        
        ExchangeItem.ItemCondition itemCondition = null;
        if (condition != null) {
            itemCondition = ExchangeItem.ItemCondition.valueOf(condition.toUpperCase());
        }
        
        Sort sortBy = switch (sort != null ? sort : "latest") {
            case "price_low" -> Sort.by(Sort.Direction.ASC, "price");
            case "price_high" -> Sort.by(Sort.Direction.DESC, "price");
            case "popular" -> Sort.by(Sort.Direction.DESC, "favoritesCount");
            default -> Sort.by(Sort.Direction.DESC, "createdAt");
        };
        
        Pageable pageable = PageRequest.of(page, size, sortBy);
        Page<ExchangeItem> items = exchangeItemRepository.findItemsWithFilters(
            exchangeCategory, location, priceMin, priceMax, itemCondition, search, pageable);
        
        User currentUser = null;
        if (currentUserEmail != null) {
            currentUser = userService.findByEmail(currentUserEmail);
        }
        
        final User finalCurrentUser = currentUser;
        return items.map(item -> {
            boolean isFavorite = finalCurrentUser != null && 
                exchangeFavoriteRepository.existsByUserIdAndItemId(finalCurrentUser.getId(), item.getId());
            boolean isAuthor = finalCurrentUser != null && 
                item.getSeller().getId().equals(finalCurrentUser.getId());
            return new ExchangeItemResponse(item, isFavorite, isAuthor);
        });
    }
    
    public ExchangeItemResponse getExchangeItem(Long id, String currentUserEmail) {
        ExchangeItem item = exchangeItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다"));
        
        // 조회수 증가
        item.setViewsCount(item.getViewsCount() + 1);
        exchangeItemRepository.save(item);
        
        User currentUser = null;
        if (currentUserEmail != null) {
            currentUser = userService.findByEmail(currentUserEmail);
        }
        
        boolean isFavorite = currentUser != null && 
            exchangeFavoriteRepository.existsByUserIdAndItemId(currentUser.getId(), item.getId());
        boolean isAuthor = currentUser != null && 
            item.getSeller().getId().equals(currentUser.getId());
        
        return new ExchangeItemResponse(item, isFavorite, isAuthor);
    }
    
    public ExchangeItem createExchangeItem(ExchangeItemRequest request, String userEmail) {
        User user = userService.findByEmail(userEmail);
        
        ExchangeItem item = new ExchangeItem();
        item.setSeller(user);
        item.setCategory(request.getCategory());
        item.setTitle(request.getTitle());
        item.setDescription(request.getDescription());
        item.setPrice(request.getPrice());
        item.setCondition(request.getCondition());
        item.setTags(request.getTags());
        item.setLocation(request.getLocation());
        
        return exchangeItemRepository.save(item);
    }
    
    public Map<String, Object> toggleExchangeFavorite(Long itemId, String userEmail) {
        User user = userService.findByEmail(userEmail);
        ExchangeItem item = exchangeItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다"));
        
        boolean isFavorite;
        String message;
        
        var existingFavorite = exchangeFavoriteRepository.findByUserIdAndItemId(user.getId(), itemId);
        if (existingFavorite.isPresent()) {
            exchangeFavoriteRepository.delete(existingFavorite.get());
            item.setFavoritesCount(item.getFavoritesCount() - 1);
            isFavorite = false;
            message = "찜하기가 취소되었습니다";
        } else {
            exchangeFavoriteRepository.save(new ExchangeFavorite(user, item));
            item.setFavoritesCount(item.getFavoritesCount() + 1);
            isFavorite = true;
            message = "찜하기가 추가되었습니다";
        }
        
        exchangeItemRepository.save(item);
        
        Map<String, Object> response = new HashMap<>();
        response.put("isFavorite", isFavorite);
        response.put("favoritesCount", item.getFavoritesCount());
        response.put("message", message);
        
        return response;
    }
}
