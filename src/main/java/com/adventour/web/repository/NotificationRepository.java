package com.adventour.web.repository;

import com.adventour.web.models.Booking;
import com.adventour.web.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<Notification> findById(Long id);
}