package com.plantcommunity.dto.comment;

import jakarta.validation.constraints.NotBlank;

public class CommentRequest {
    @NotBlank(message = "댓글 내용은 필수입니다")
    private String content;
    
    private Long parentId;

    // Constructors
    public CommentRequest() {}

    // Getters and Setters
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
}
