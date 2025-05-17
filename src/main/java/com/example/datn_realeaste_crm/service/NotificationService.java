package com.example.datn_realeaste_crm.service;


import com.example.datn_realeaste_crm.dto.request.NotificationRequest;
import com.example.datn_realeaste_crm.dto.response.NotificationResponse;
import com.example.datn_realeaste_crm.entity.Notification;
import com.example.datn_realeaste_crm.entity.User;
import com.example.datn_realeaste_crm.exception.ResourceNotFoundException;
import com.example.datn_realeaste_crm.repository.NotificationRepository;
import com.example.datn_realeaste_crm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {
    
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    
    public Page<NotificationResponse> getUserNotifications(Integer userId, String status, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        
        Page<Notification> notifications;
        if (status != null) {
            notifications = notificationRepository.findByUserUserIdAndStatus(userId, status, pageable);
        } else {
            notifications = notificationRepository.findByUserUserId(userId, pageable);
        }
        
        return notifications.map(this::convertToNotificationResponse);
    }
    
    @Transactional
    public NotificationResponse createNotification(NotificationRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));
        
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(request.getMessage());
        notification.setStatus(Notification.NotificationStatus.valueOf("Chưa đọc"));
        notification.setCreatedAt(LocalDateTime.now());
        
        Notification savedNotification = notificationRepository.save(notification);
        
        return convertToNotificationResponse(savedNotification);
    }
    
    @Transactional
    public NotificationResponse markAsRead(Integer id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
        
        notification.setStatus(Notification.NotificationStatus.valueOf("Đã đọc"));
        Notification updatedNotification = notificationRepository.save(notification);
        
        return convertToNotificationResponse(updatedNotification);
    }
    
    @Transactional
    public void markAllAsRead(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        
        notificationRepository.updateStatusForUser(userId, "Đã đọc");
    }
    
    private NotificationResponse convertToNotificationResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getNotificationId())
                .userId(notification.getUser().getUserId())
                .message(notification.getMessage())
                .status(String.valueOf(notification.getStatus()))
                .createdAt(notification.getCreatedAt())
                .build();
    }
}