package com.adventour.web.service.impl;

import com.adventour.web.dto.NotificationDto;
import com.adventour.web.models.Notification;
import com.adventour.web.repository.NotificationRepository;
import com.adventour.web.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;

public class NotificationServiceImpl implements NotificationService {
    private NotificationRepository notificationRepository;

    @Autowired
    private  NotificationServiceImpl(NotificationRepository notificationRepository){
        this.notificationRepository = notificationRepository;
    }

    private NotificationDto mapToNotificationDto(Notification notification){
        return NotificationDto.builder()
                .id(notification.getId())
                .description(notification.getDescription())
                .createdTime(notification.getCreatedTime())
                .isRead(notification.getIsRead())
                .build();
    }
}
