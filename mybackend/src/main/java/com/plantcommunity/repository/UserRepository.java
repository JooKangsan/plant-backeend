package com.plantcommunity.repository;

import com.plantcommunity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    
    @Query("SELECT COUNT(p) FROM Post p WHERE p.user.id = :userId")
    int countPostsByUserId(@Param("userId") Long userId);
}
