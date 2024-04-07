package com.adventour.web.repository;

import com.adventour.web.models.Booking;
import com.adventour.web.models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    Optional<Passenger> findById(Long id);
}