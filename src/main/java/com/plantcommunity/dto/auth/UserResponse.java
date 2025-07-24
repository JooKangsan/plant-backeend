package com.plantcommunity.dto.auth;

import com.plantcommunity.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public class UserResponse {
    private Long id;
    private String email;
    private String nickname;
    private String profileImage;
    private List<String> interests;
    private int plantsCount;
    private int postsCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public UserResponse() {}

    public UserResponse(User user, int postsCount) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.profileImage = user.getProfileImage();
        this.interests = user.getInterests();
        this.plantsCount = 0; // TODO: 실제 식물 수 계산
        this.postsCount = postsCount;
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }

    public List<String> getInterests() { return interests; }
    public void setInterests(List<String> interests) { this.interests = interests; }

    public int getPlantsCount() { return plantsCount; }
    public void setPlantsCount(int plantsCount) { this.plantsCount = plantsCount; }

    public int getPostsCount() { return postsCount; }
    public void setPostsCount(int postsCount) { this.postsCount = postsCount; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
