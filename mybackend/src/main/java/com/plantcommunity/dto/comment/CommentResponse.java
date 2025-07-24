package com.plantcommunity.dto.comment;

import com.plantcommunity.entity.Comment;
import com.plantcommunity.entity.User;

import java.time.LocalDateTime;

public class CommentResponse {
    private Long id;
    private UserInfo user;
    private String content;
    private int likesCount;
    private boolean isLiked;
    private boolean isAuthor;
    private Long parentId;
    private int repliesCount;
    private LocalDateTime createdAt;

    public static class UserInfo {
        private Long id;
        private String nickname;
        private String profileImage;
        private String level = "초급자"; // TODO: 실제 레벨 계산

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

        public String getLevel() { return level; }
        public void setLevel(String level) { this.level = level; }
    }

    // Constructors
    public CommentResponse() {}

    public CommentResponse(Comment comment, boolean isLiked, boolean isAuthor) {
        this.id = comment.getId();
        this.user = new UserInfo(comment.getUser());
        this.content = comment.getContent();
        this.likesCount = comment.getLikesCount();
        this.isLiked = isLiked;
        this.isAuthor = isAuthor;
        this.parentId = comment.getParent() != null ? comment.getParent().getId() : null;
        this.repliesCount = comment.getRepliesCount();
        this.createdAt = comment.getCreatedAt();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UserInfo getUser() { return user; }
    public void setUser(UserInfo user) { this.user = user; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public int getLikesCount() { return likesCount; }
    public void setLikesCount(int likesCount) { this.likesCount = likesCount; }

    public boolean isLiked() { return isLiked; }
    public void setLiked(boolean liked) { isLiked = liked; }

    public boolean isAuthor() { return isAuthor; }
    public void setAuthor(boolean author) { isAuthor = author; }

    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }

    public int getRepliesCount() { return repliesCount; }
    public void setRepliesCount(int repliesCount) { this.repliesCount = repliesCount; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
