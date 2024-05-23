package com.adventour.web.repository;

import com.adventour.web.dto.TourDto;
import com.adventour.web.models.Schedule;
import com.adventour.web.models.Tour;
import com.adventour.web.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TripRepository extends JpaRepository<Trip, Long> {
    Optional<Trip> findById(Long id);

    Set<Trip> findByTour (Tour tour);
}
