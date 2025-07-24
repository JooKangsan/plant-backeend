package com.plantcommunity.repository;

import com.plantcommunity.entity.ExchangeItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeItemRepository extends JpaRepository<ExchangeItem, Long> {
    
    @Query("SELECT e FROM ExchangeItem e WHERE " +
           "(:category IS NULL OR e.category = :category) AND " +
           "(:location IS NULL OR e.location LIKE %:location%) AND " +
           "(:priceMin IS NULL OR e.price >= :priceMin) AND " +
           "(:priceMax IS NULL OR e.price <= :priceMax) AND " +
           "(:condition IS NULL OR e.condition = :condition) AND " +
           "(:search IS NULL OR e.title LIKE %:search% OR e.description LIKE %:search%) AND " +
           "e.isAvailable = true")
    Page<ExchangeItem> findItemsWithFilters(
        @Param("category") ExchangeItem.ExchangeCategory category,
        @Param("location") String location,
        @Param("priceMin") Integer priceMin,
        @Param("priceMax") Integer priceMax,
        @Param("condition") ExchangeItem.ItemCondition condition,
        @Param("search") String search,
        Pageable pageable
    );
}
