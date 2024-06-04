package com.adventour.web.repository;

import com.adventour.web.models.Booking;
import com.adventour.web.models.Schedule;
import com.adventour.web.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findById(Long id);

    List<Ticket> findByBooking(Booking booking);

    @Override
    long count();

}