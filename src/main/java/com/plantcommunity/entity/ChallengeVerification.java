package com.plantcommunity.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "challenge_verifications")
@EntityListeners(AuditingEntityListener.class)
public class ChallengeVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participation_id", nullable = false)
    private ChallengeParticipation participation;

    private String image;
    private String comment;
    private int pointsEarned = 10;

    @CreatedDate
    private LocalDateTime createdAt;

    // Constructors
    public ChallengeVerification() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ChallengeParticipation getParticipation() { return participation; }
    public void setParticipation(ChallengeParticipation participation) { this.participation = participation; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public int getPointsEarned() { return pointsEarned; }
    public void setPointsEarned(int pointsEarned) { this.pointsEarned = pointsEarned; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
