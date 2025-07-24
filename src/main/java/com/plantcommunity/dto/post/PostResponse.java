package com.plantcommunity.dto.post;

import com.plantcommunity.entity.Post;
import com.plantcommunity.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public class PostResponse {
    private Long id;
    private UserInfo user;
    private String category;
    private String title;
    private String content;
    private List<String> images;
    private List<String> tags;
    private int likesCount;
    private int commentsCount;
    private boolean isLiked;
    private boolean isSolved;
    private boolean isPinned;
    private boolean isAuthor;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static class UserInfo {
        private Long id;
        private String nickname;
        private String profileImage;

        public UserInfo(User user) {
            this.id = user.getId();
            this.nickname = user.getNickname();
            this.profileImage = user.getProfileImage();
        }

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getNickname() { return nickname; }
        public void setNickname(String nickname) { this.nickname = nickname; }

        public String getProfileImage() { return profileImage; }
        public void setProfileImage(String profileImage) { this.profileImage = profileImage; }
    }

    // Constructors
    public PostResponse() {}

    public PostResponse(Post post, boolean isLiked, boolean isAuthor) {
        this.id = post.getId();
        this.user = new UserInfo(post.getUser());
        this.category = post.getCategory().name().toLowerCase();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.images = post.getImages();
        this.tags = post.getTags();
        this.likesCount = post.getLikesCount();
        this.commentsCount = post.getCommentsCount();
        this.isLiked = isLiked;
        this.isSolved = post.isSolved();
        this.isPinned = post.isPinned();
        this.isAuthor = isAuthor;
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UserInfo getUser() { return user; }
    public void setUser(UserInfo user) { this.user = user; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public int getLikesCount() { return likesCount; }
    public void setLikesCount(int likesCount) { this.likesCount = likesCount; }

    public int getCommentsCount() { return commentsCount; }
    public void setCommentsCount(int commentsCount) { this.commentsCount = commentsCount; }

    public boolean isLiked() { return isLiked; }
    public void setLiked(boolean liked) { isLiked = liked; }

    public boolean isSolved() { return isSolved; }
    public void setSolved(boolean solved) { isSolved = solved; }

    public boolean isPinned() { return isPinned; }
    public void setPinned(boolean pinned) { isPinned = pinned; }

    public boolean isAuthor() { return isAuthor; }
    public void setAuthor(boolean author) { isAuthor = author; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
