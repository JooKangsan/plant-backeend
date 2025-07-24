package com.plantcommunity.repository;

import com.plantcommunity.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId AND " +
           "(:type IS NULL OR n.type = :type) AND " +
           "(:isRead IS NULL OR n.isRead = :isRead)")
    Page<Notification> findNotificationsWithFilters(
        @Param("userId") Long userId,
        @Param("type") Notification.NotificationType type,
        @Param("isRead") Boolean isRead,
        Pageable pageable
    );
}
