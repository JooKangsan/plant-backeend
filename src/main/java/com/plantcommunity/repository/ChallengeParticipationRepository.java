package com.plantcommunity.repository;

import com.plantcommunity.entity.ChallengeParticipation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChallengeParticipationRepository extends JpaRepository<ChallengeParticipation, Long> {
    Optional<ChallengeParticipation> findByUserIdAndChallengeId(Long userId, Long challengeId);
    boolean existsByUserIdAndChallengeId(Long userId, Long challengeId);
    Page<ChallengeParticipation> findByUserIdAndStatus(Long userId, ChallengeParticipation.ParticipationStatus status, Pageable pageable);
}
