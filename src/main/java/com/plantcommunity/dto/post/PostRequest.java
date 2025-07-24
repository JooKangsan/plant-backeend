package com.plantcommunity.dto.post;

import com.plantcommunity.entity.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class PostRequest {
    @NotNull(message = "카테고리는 필수입니다")
    private Post.PostCategory category;

    @NotBlank(message = "제목은 필수입니다")
    private String title;

    @NotBlank(message = "내용은 필수입니다")
    private String content;

    private List<String> tags;

    // Constructors
    public PostRequest() {}

    // Getters and Setters
    public Post.PostCategory getCategory() { return category; }
    public void setCategory(Post.PostCategory category) { this.category = category; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
}
