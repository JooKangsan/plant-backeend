package com.plantcommunity.controller;

import com.plantcommunity.dto.post.PostRequest;
import com.plantcommunity.dto.post.PostResponse;
import com.plantcommunity.entity.Post;
import com.plantcommunity.service.PostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    
    private final PostService postService;
    
    public PostController(PostService postService) {
        this.postService = postService;
    }
    
    @GetMapping
    public ResponseEntity<Page<PostResponse>> getPosts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "latest") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication != null ? authentication.getName() : null;
        
        Page<PostResponse> posts = postService.getPosts(category, search, sort, page, size, currentUserEmail);
        return ResponseEntity.ok(posts);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication != null ? authentication.getName() : null;
        
        PostResponse post = postService.getPost(id, currentUserEmail);
        return ResponseEntity.ok(post);
    }
    
    @PostMapping
    public ResponseEntity<Map<String, Object>> createPost(@Valid @RequestBody PostRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        
        Post post = postService.createPost(request, userEmail);
        
        Map<String, Object> response = new HashMap<>();
        response.put("id", post.getId());
        response.put("message", "게시물이 작성되었습니다");
        
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updatePost(@PathVariable Long id, 
                                                        @Valid @RequestBody PostRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        
        postService.updatePost(id, request, userEmail);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "게시물이 수정되었습니다");
        
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletePost(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        
        postService.deletePost(id, userEmail);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "게시물이 삭제되었습니다");
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Map<String, Object>> togglePostLike(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        
        Map<String, Object> response = postService.togglePostLike(id, userEmail);
        return ResponseEntity.ok(response);
    }
}
