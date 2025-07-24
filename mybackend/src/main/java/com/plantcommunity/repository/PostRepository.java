package com.plantcommunity.repository;

import com.plantcommunity.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
    @Query("SELECT p FROM Post p WHERE " +
           "(:category IS NULL OR p.category = :category) AND " +
           "(:search IS NULL OR p.title LIKE %:search% OR p.content LIKE %:search%)")
    Page<Post> findPostsWithFilters(
        @Param("category") Post.PostCategory category,
        @Param("search") String search,
        Pageable pageable
    );
    
    Page<Post> findByUserId(Long userId, Pageable pageable);
}
