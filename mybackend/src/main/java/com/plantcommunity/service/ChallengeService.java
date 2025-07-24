package com.plantcommunity.service;

import com.plantcommunity.entity.Challenge;
import com.plantcommunity.entity.ChallengeParticipation;
import com.plantcommunity.entity.ChallengeVerification;
import com.plantcommunity.entity.User;
import com.plantcommunity.repository.ChallengeParticipationRepository;
import com.plantcommunity.repository.ChallengeRepository;
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
public class ChallengeService {
    
    private final ChallengeRepository challengeRepository;
    private final ChallengeParticipationRepository participationRepository;
    private final UserService userService;
    
    public ChallengeService(ChallengeRepository challengeRepository,
                           ChallengeParticipationRepository participationRepository,
                           UserService userService) {
        this.challengeRepository = challengeRepository;
        this.participationRepository = participationRepository;
        this.userService = userService;
    }
    
    public Page<Challenge> getChallenges(String status, String category, String difficulty,
                                        String sort, int page, int size) {
        
        Challenge.ChallengeStatus challengeStatus = null;
        if (status != null && !status.equals("all")) {
            challengeStatus = Challenge.ChallengeStatus.valueOf(status.toUpperCase());
        }
        
        Challenge.ChallengeCategory challengeCategory = null;
        if (category != null) {
            challengeCategory = Challenge.ChallengeCategory.valueOf(category.toUpperCase());
        }
        
        Challenge.ChallengeDifficulty challengeDifficulty = null;
        if (difficulty != null) {
            challengeDifficulty = Challenge.ChallengeDifficulty.valueOf(difficulty.toUpperCase());
        }
        
        Sort sortBy = switch (sort != null ? sort : "latest") {
            case "popular" -> Sort.by(Sort.Direction.DESC, "participantsCount");
            case "deadline" -> Sort.by(Sort.Direction.ASC, "endDate");
            default -> Sort.by(Sort.Direction.DESC, "createdAt");
        };
        
        Pageable pageable = PageRequest.of(page, size, sortBy);
        return challengeRepository.findChallengesWithFilters(challengeStatus, challengeCategory, challengeDifficulty, pageable);
    }
    
    public Challenge getChallenge(Long id) {
        return challengeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("챌린지를 찾을 수 없습니다"));
    }
    
    public Map<String, String> joinChallenge(Long challengeId, String userEmail) {
        User user = userService.findByEmail(userEmail);
        Challenge challenge = getChallenge(challengeId);
        
        if (participationRepository.existsByUserIdAndChallengeId(user.getId(), challengeId)) {
            throw new RuntimeException("이미 참여 중인 챌린지입니다");
        }
        
        ChallengeParticipation participation = new ChallengeParticipation(user, challenge);
        participationRepository.save(participation);
        
        challenge.setParticipantsCount(challenge.getParticipantsCount() + 1);
        challengeRepository.save(challenge);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "챌린지에 참여했습니다");
        return response;
    }
    
    public Map<String, Object> verifyChallenge(Long challengeId, String userEmail, String comment) {
        User user = userService.findByEmail(userEmail);
        ChallengeParticipation participation = participationRepository
                .findByUserIdAndChallengeId(user.getId(), challengeId)
                .orElseThrow(() -> new RuntimeException("참여 중인 챌린지가 아닙니다"));
        
        if (participation.isTodayCompleted()) {
            throw new RuntimeException("오늘은 이미 인증을 완료했습니다");
        }
        
        ChallengeVerification verification = new ChallengeVerification();
        verification.setParticipation(participation);
        verification.setComment(comment);
        // TODO: 이미지 처리
        
        participation.setCompletedDays(participation.getCompletedDays() + 1);
        participation.setTodayCompleted(true);
        participation.setStreak(participation.getStreak() + 1);
        
        participationRepository.save(participation);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "오늘의 챌린지가 인증되었습니다");
        response.put("pointsEarned", verification.getPointsEarned());
        
        return response;
    }
    
    public Page<ChallengeParticipation> getMyChallenges(String status, int page, int size, String userEmail) {
        User user = userService.findByEmail(userEmail);
        
        ChallengeParticipation.ParticipationStatus participationStatus = null;
        if (status != null) {
            participationStatus = ChallengeParticipation.ParticipationStatus.valueOf(status.toUpperCase());
        }
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "joinedAt"));
        
        if (participationStatus != null) {
            return participationRepository.findByUserIdAndStatus(user.getId(), participationStatus, pageable);
        } else {
            // TODO: 모든 상태의 참여 챌린지 조회
            return participationRepository.findByUserIdAndStatus(user.getId(), 
                ChallengeParticipation.ParticipationStatus.ONGOING, pageable);
        }
    }
}
