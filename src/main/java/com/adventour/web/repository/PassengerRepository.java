package com.adventour.web.repository;

import com.adventour.web.models.Booking;
import com.adventour.web.models.Passenger;
import com.adventour.web.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    Optional<Passenger> findById(Long id);

    List<Passenger> findByBooking(Booking booking);
    List<Passenger> findByTrip (Trip trip);


}