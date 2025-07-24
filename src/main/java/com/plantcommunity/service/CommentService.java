package com.plantcommunity.service;

import com.plantcommunity.dto.comment.CommentRequest;
import com.plantcommunity.dto.comment.CommentResponse;
import com.plantcommunity.entity.Comment;
import com.plantcommunity.entity.CommentLike;
import com.plantcommunity.entity.Post;
import com.plantcommunity.entity.User;
import com.plantcommunity.repository.CommentLikeRepository;
import com.plantcommunity.repository.CommentRepository;
import com.plantcommunity.repository.PostRepository;
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
public class CommentService {
    
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostRepository postRepository;
    private final UserService userService;
    
    public CommentService(CommentRepository commentRepository, CommentLikeRepository commentLikeRepository,
                         PostRepository postRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.commentLikeRepository = commentLikeRepository;
        this.postRepository = postRepository;
        this.userService = userService;
    }
    
    public Page<CommentResponse> getComments(Long postId, int page, int size, String currentUserEmail) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));
        Page<Comment> comments = commentRepository.findByPostIdAndParentIsNull(postId, pageable);
        
        User currentUser = null;
        if (currentUserEmail != null) {
            currentUser = userService.findByEmail(currentUserEmail);
        }
        
        final User finalCurrentUser = currentUser;
        return comments.map(comment -> {
            boolean isLiked = finalCurrentUser != null && 
                commentLikeRepository.existsByUserIdAndCommentId(finalCurrentUser.getId(), comment.getId());
            boolean isAuthor = finalCurrentUser != null && 
                comment.getUser().getId().equals(finalCurrentUser.getId());
            return new CommentResponse(comment, isLiked, isAuthor);
        });
    }
    
    public Comment createComment(Long postId, CommentRequest request, String userEmail) {
        User user = userService.findByEmail(userEmail);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다"));
        
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setContent(request.getContent());
        
        if (request.getParentId() != null) {
            Comment parent = commentRepository.findById(request.getParentId())
                    .orElseThrow(() -> new RuntimeException("부모 댓글을 찾을 수 없습니다"));
            comment.setParent(parent);
            parent.setRepliesCount(parent.getRepliesCount() + 1);
            commentRepository.save(parent);
        }
        
        Comment savedComment = commentRepository.save(comment);
        
        // 게시물의 댓글 수 증가
        post.setCommentsCount(post.getCommentsCount() + 1);
        postRepository.save(post);
        
        return savedComment;
    }
    
    public Map<String, Object> toggleCommentLike(Long commentId, String userEmail) {
        User user = userService.findByEmail(userEmail);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다"));
        
        boolean isLiked;
        String message;
        
        var existingLike = commentLikeRepository.findByUserIdAndCommentId(user.getId(), commentId);
        if (existingLike.isPresent()) {
            commentLikeRepository.delete(existingLike.get());
            comment.setLikesCount(comment.getLikesCount() - 1);
            isLiked = false;
            message = "댓글 좋아요가 취소되었습니다";
        } else {
            commentLikeRepository.save(new CommentLike(user, comment));
            comment.setLikesCount(comment.getLikesCount() + 1);
            isLiked = true;
            message = "댓글 좋아요가 추가되었습니다";
        }
        
        commentRepository.save(comment);
        
        Map<String, Object> response = new HashMap<>();
        response.put("isLiked", isLiked);
        response.put("likesCount", comment.getLikesCount());
        response.put("message", message);
        
        return response;
    }
}
