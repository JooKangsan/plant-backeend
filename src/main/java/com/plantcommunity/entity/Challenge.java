package com.plantcommunity.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "challenges")
@EntityListeners(AuditingEntityListener.class)
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "challenge_image")  // 컬럼명 명시적 지정
    private String image;

    @Enumerated(EnumType.STRING)
    private ChallengeCategory category;

    @Enumerated(EnumType.STRING)
    private ChallengeDifficulty difficulty;

    private int duration; // 일수

    private int participantsCount = 0;

    @Embedded
    private ChallengeReward reward;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ElementCollection
    @CollectionTable(name = "challenge_rules", joinColumns = @JoinColumn(name = "challenge_id"))
    @Column(name = "rule")
    private List<String> rules;

    private boolean isPopular = false;

    @Enumerated(EnumType.STRING)
    private ChallengeStatus status = ChallengeStatus.AVAILABLE;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public enum ChallengeCategory {
        CARE, GROWTH, CREATIVE, DIY, RECORD
    }

    public enum ChallengeDifficulty {
        EASY, NORMAL, HARD
    }

    public enum ChallengeStatus {
        AVAILABLE, ONGOING, COMPLETED, EXPIRED
    }

    @Embeddable
    public static class ChallengeReward {
        @Enumerated(EnumType.STRING)
        private RewardType type;

        private String name;

        @Column(name = "reward_image")  // 다른 컬럼명으로 매핑
        private String image;

        public enum RewardType {
            BADGE, POINTS, ITEM
        }

        // Constructors
        public ChallengeReward() {}

        // Getters and Setters
        public RewardType getType() { return type; }
        public void setType(RewardType type) { this.type = type; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getImage() { return image; }
        public void setImage(String image) { this.image = image; }
    }

    // Constructors
    public Challenge() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public ChallengeCategory getCategory() { return category; }
    public void setCategory(ChallengeCategory category) { this.category = category; }

    public ChallengeDifficulty getDifficulty() { return difficulty; }
    public void setDifficulty(ChallengeDifficulty difficulty) { this.difficulty = difficulty; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public int getParticipantsCount() { return participantsCount; }
    public void setParticipantsCount(int participantsCount) { this.participantsCount = participantsCount; }

    public ChallengeReward getReward() { return reward; }
    public void setReward(ChallengeReward reward) { this.reward = reward; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public List<String> getRules() { return rules; }
    public void setRules(List<String> rules) { this.rules = rules; }

    public boolean isPopular() { return isPopular; }
    public void setPopular(boolean popular) { isPopular = popular; }

    public ChallengeStatus getStatus() { return status; }
    public void setStatus(ChallengeStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
