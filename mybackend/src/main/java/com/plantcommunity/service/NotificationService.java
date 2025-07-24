package com.plantcommunity.service;

import com.plantcommunity.entity.Notification;
import com.plantcommunity.entity.User;
import com.plantcommunity.repository.NotificationRepository;
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
public class NotificationService {
    
    private final NotificationRepository notificationRepository;
    private final UserService userService;
    
    public NotificationService(NotificationRepository notificationRepository, UserService userService) {
        this.notificationRepository = notificationRepository;
        this.userService = userService;
    }
    
    public Page<Notification> getNotifications(String type, Boolean isRead, int page, int size, String userEmail) {
        User user = userService.findByEmail(userEmail);
        
        Notification.NotificationType notificationType = null;
        if (type != null && !type.equals("all")) {
            notificationType = Notification.NotificationType.valueOf(type.toUpperCase());
        }
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return notificationRepository.findNotificationsWithFilters(user.getId(), notificationType, isRead, pageable);
    }
    
    public Map<String, String> markAsRead(Long notificationId, String userEmail) {
        User user = userService.findByEmail(userEmail);
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("알림을 찾을 수 없습니다"));
        
        if (!notification.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("알림에 접근할 권한이 없습니다");
        }
        
        notification.setRead(true);
        notificationRepository.save(notification);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "알림이 읽음 처리되었습니다");
        return response;
    }
    
    public void createNotification(User user, Notification.NotificationType type, String title, String message) {
        Notification notification = new Notification(user, type, title, message);
        notificationRepository.save(notification);
    }
}
