package com.plantcommunity.repository;

import com.plantcommunity.entity.ExchangeFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeFavoriteRepository extends JpaRepository<ExchangeFavorite, Long> {
    Optional<ExchangeFavorite> findByUserIdAndItemId(Long userId, Long itemId);
    boolean existsByUserIdAndItemId(Long userId, Long itemId);
    int countByItemId(Long itemId);
}
