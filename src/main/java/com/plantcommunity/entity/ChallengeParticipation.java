package com.plantcommunity.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "challenge_participations", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "challenge_id"})
})
@EntityListeners(AuditingEntityListener.class)
public class ChallengeParticipation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id", nullable = false)
    private Challenge challenge;

    private int completedDays = 0;
    private boolean todayCompleted = false;
    private int streak = 0;

    @Enumerated(EnumType.STRING)
    private ParticipationStatus status = ParticipationStatus.ONGOING;

    @CreatedDate
    private LocalDateTime joinedAt;

    public enum ParticipationStatus {
        ONGOING, COMPLETED, FAILED
    }

    // Constructors
    public ChallengeParticipation() {}

    public ChallengeParticipation(User user, Challenge challenge) {
        this.user = user;
        this.challenge = challenge;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Challenge getChallenge() { return challenge; }
    public void setChallenge(Challenge challenge) { this.challenge = challenge; }

    public int getCompletedDays() { return completedDays; }
    public void setCompletedDays(int completedDays) { this.completedDays = completedDays; }

    public boolean isTodayCompleted() { return todayCompleted; }
    public void setTodayCompleted(boolean todayCompleted) { this.todayCompleted = todayCompleted; }

    public int getStreak() { return streak; }
    public void setStreak(int streak) { this.streak = streak; }

    public ParticipationStatus getStatus() { return status; }
    public void setStatus(ParticipationStatus status) { this.status = status; }

    public LocalDateTime getJoinedAt() { return joinedAt; }
    public void setJoinedAt(LocalDateTime joinedAt) { this.joinedAt = joinedAt; }
}
