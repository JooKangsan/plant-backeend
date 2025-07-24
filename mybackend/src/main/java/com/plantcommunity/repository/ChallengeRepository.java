package com.plantcommunity.repository;

import com.plantcommunity.entity.Challenge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    
    @Query("SELECT c FROM Challenge c WHERE " +
           "(:status IS NULL OR c.status = :status) AND " +
           "(:category IS NULL OR c.category = :category) AND " +
           "(:difficulty IS NULL OR c.difficulty = :difficulty)")
    Page<Challenge> findChallengesWithFilters(
        @Param("status") Challenge.ChallengeStatus status,
        @Param("category") Challenge.ChallengeCategory category,
        @Param("difficulty") Challenge.ChallengeDifficulty difficulty,
        Pageable pageable
    );
}
