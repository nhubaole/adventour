package com.adventour.web.repository;

import com.adventour.web.models.Schedule;
import com.adventour.web.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findById(Long id);
}