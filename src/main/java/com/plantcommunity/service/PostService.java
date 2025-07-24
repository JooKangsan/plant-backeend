package com.plantcommunity.service;

import com.plantcommunity.dto.post.PostRequest;
import com.plantcommunity.dto.post.PostResponse;
import com.plantcommunity.entity.Post;
import com.plantcommunity.entity.User;
import com.plantcommunity.entity.PostLike;
import com.plantcommunity.repository.PostRepository;
import com.plantcommunity.repository.PostLikeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class PostService {
    
    private final PostRepository postRepository;
    private final UserService userService;
    private final PostLikeRepository postLikeRepository;
    
    public PostService(PostRepository postRepository, UserService userService, PostLikeRepository postLikeRepository) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.postLikeRepository = postLikeRepository;
    }
    
    public Page<PostResponse> getPosts(String category, String search, String sort, 
                                     int page, int size, String currentUserEmail) {
        
        Post.PostCategory postCategory = null;
        if (category != null && !category.equals("all")) {
            postCategory = Post.PostCategory.valueOf(category.toUpperCase());
        }
        
        Sort sortBy = switch (sort != null ? sort : "latest") {
            case "popular" -> Sort.by(Sort.Direction.DESC, "likesCount");
            case "oldest" -> Sort.by(Sort.Direction.ASC, "createdAt");
            default -> Sort.by(Sort.Direction.DESC, "createdAt");
        };
        
        Pageable pageable = PageRequest.of(page, size, sortBy);
        Page<Post> posts = postRepository.findPostsWithFilters(postCategory, search, pageable);
        
        User currentUser = null;
        if (currentUserEmail != null) {
            currentUser = userService.findByEmail(currentUserEmail);
        }
        
        final User finalCurrentUser = currentUser;
        return posts.map(post -> {
            boolean isLiked = finalCurrentUser != null && 
                postLikeRepository.existsByUserIdAndPostId(finalCurrentUser.getId(), post.getId());
            boolean isAuthor = finalCurrentUser != null && 
                post.getUser().getId().equals(finalCurrentUser.getId());
            return new PostResponse(post, isLiked, isAuthor);
        });
    }
    
    public PostResponse getPost(Long id, String currentUserEmail) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다"));
        
        User currentUser = null;
        if (currentUserEmail != null) {
            currentUser = userService.findByEmail(currentUserEmail);
        }
        
        boolean isLiked = currentUser != null && 
            postLikeRepository.existsByUserIdAndPostId(currentUser.getId(), post.getId());
        boolean isAuthor = currentUser != null && 
            post.getUser().getId().equals(currentUser.getId());
        
        return new PostResponse(post, isLiked, isAuthor);
    }
    
    public Post createPost(PostRequest request, String userEmail) {
        User user = userService.findByEmail(userEmail);
        
        Post post = new Post();
        post.setUser(user);
        post.setCategory(request.getCategory());
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setTags(request.getTags());
        
        return postRepository.save(post);
    }
    
    public Post updatePost(Long id, PostRequest request, String userEmail) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다"));
        
        User user = userService.findByEmail(userEmail);
        if (!post.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("게시물을 수정할 권한이 없습니다");
        }
        
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setTags(request.getTags());
        
        return postRepository.save(post);
    }
    
    public void deletePost(Long id, String userEmail) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다"));
        
        User user = userService.findByEmail(userEmail);
        if (!post.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("게시물을 삭제할 권한이 없습니다");
        }
        
        postRepository.delete(post);
    }

    public Map<String, Object> togglePostLike(Long postId, String userEmail) {
        User user = userService.findByEmail(userEmail);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다"));
        
        boolean isLiked;
        String message;
        
        var existingLike = postLikeRepository.findByUserIdAndPostId(user.getId(), postId);
        if (existingLike.isPresent()) {
            postLikeRepository.delete(existingLike.get());
            post.setLikesCount(post.getLikesCount() - 1);
            isLiked = false;
            message = "좋아요가 취소되었습니다";
        } else {
            postLikeRepository.save(new PostLike(user, post));
            post.setLikesCount(post.getLikesCount() + 1);
            isLiked = true;
            message = "좋아요가 추가되었습니다";
        }
        
        postRepository.save(post);
        
        Map<String, Object> response = new HashMap<>();
        response.put("isLiked", isLiked);
        response.put("likesCount", post.getLikesCount());
        response.put("message", message);
        
        return response;
    }
}
