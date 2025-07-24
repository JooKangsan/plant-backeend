package com.plantcommunity.controller;

import com.plantcommunity.dto.comment.CommentRequest;
import com.plantcommunity.dto.comment.CommentResponse;
import com.plantcommunity.entity.Comment;
import com.plantcommunity.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {
    
    private final CommentService commentService;
    
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    
    @GetMapping
    public ResponseEntity<Page<CommentResponse>> getComments(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication != null ? authentication.getName() : null;
        
        Page<CommentResponse> comments = commentService.getComments(postId, page, size, currentUserEmail);
        return ResponseEntity.ok(comments);
    }
    
    @PostMapping
    public ResponseEntity<Map<String, Object>> createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequest request) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        
        Comment comment = commentService.createComment(postId, request, userEmail);
        
        Map<String, Object> response = new HashMap<>();
        response.put("id", comment.getId());
        response.put("message", "댓글이 작성되었습니다");
        
        return ResponseEntity.ok(response);
    }
}

@RestController
@RequestMapping("/api/comments")
class CommentLikeController {
    
    private final CommentService commentService;
    
    public CommentLikeController(CommentService commentService) {
        this.commentService = commentService;
    }
    
    @PostMapping("/{id}/like")
    public ResponseEntity<Map<String, Object>> toggleCommentLike(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        
        Map<String, Object> response = commentService.toggleCommentLike(id, userEmail);
        return ResponseEntity.ok(response);
    }
}
