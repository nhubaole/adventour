package com.adventour.web.repository;

import com.adventour.web.dto.TourDto;
import com.adventour.web.models.Schedule;
import com.adventour.web.models.Tour;
import com.adventour.web.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TripRepository extends JpaRepository<Trip, Long> {
    Optional<Trip> findById(Long id);

    Set<Trip> findByTour (Tour tour);

    @Query("SELECT COUNT(t) FROM Trip t WHERE FUNCTION('DATE', t.startDate) = CURRENT_DATE")
    long countTripsStartingToday();

    @Query("SELECT t FROM Trip t WHERE t.startDate >= :startOfMonth AND t.startDate <= :endOfMonth")
    List<Trip> findTripsForCurrentMonth(@Param("startOfMonth") LocalDateTime startOfMonth, @Param("endOfMonth") LocalDateTime endOfMonth);

    @Query("SELECT COUNT(t) FROM Trip t WHERE t.startDate < :today")
    long countTotalTripsBeforeToday(@Param("today") LocalDateTime today);

    @Query("SELECT COUNT(t) FROM Trip t WHERE t.startDate < :today AND (t.status <> 'CANCELLED' OR t.status is NULL)")
    long countSuccessfulTripsBeforeToday(@Param("today") LocalDateTime today);

}
