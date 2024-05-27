package com.adventour.web.repository;

import com.adventour.web.models.Booking;
import com.adventour.web.models.Customer;
import com.adventour.web.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
     Optional<Booking> findById(Long id);

    List<Booking> findByBookingDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Booking> findByCustomer(Customer customer);
    List<Booking> findByTrip(Trip trip);



}
