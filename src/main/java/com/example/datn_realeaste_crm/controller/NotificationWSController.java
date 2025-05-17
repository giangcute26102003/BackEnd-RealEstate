package com.example.datn_realeaste_crm.controller;


import com.example.datn_realeaste_crm.dto.request.NotificationRequest;
import com.example.datn_realeaste_crm.dto.response.NotificationResponse;
import com.example.datn_realeaste_crm.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class NotificationWSController {
    
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;
    
    @MessageMapping("/notifications.send")
    public void sendNotification(@Payload NotificationRequest request) {
        NotificationResponse notification = notificationService.createNotification(request);
        
        // Send to specific user's queue
        messagingTemplate.convertAndSendToUser(
                notification.getUserId().toString(),
                "/queue/notifications",
                notification
        );
        
        // Also broadcast to topic for admins/managers
        messagingTemplate.convertAndSend("/topic/notifications", notification);
    }
}