package com.adventour.web.repository;

import com.adventour.web.models.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TourRepository extends JpaRepository<Tour, Long> {
    Optional<Tour> findById(Long id);

}
