package com.adventour.web.repository;

import com.adventour.web.models.Booking;
import com.adventour.web.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findById(Long id);
}