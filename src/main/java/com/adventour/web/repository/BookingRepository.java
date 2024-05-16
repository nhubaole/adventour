package com.adventour.web.repository;

import com.adventour.web.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
     Optional<Booking> findById(Long id);

    List<Booking> findByBookingDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Booking> findByCustomerId(Long customerId);
    List<Booking> findByTripId(Long tripId);



}
