package com.adventour.web.repository;

import com.adventour.web.models.Schedule;
import com.adventour.web.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip, Long> {
    Optional<Trip> findById(Long id);

}
