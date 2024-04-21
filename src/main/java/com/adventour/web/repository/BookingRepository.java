package com.adventour.web.repository;

import com.adventour.web.models.Account;
import com.adventour.web.models.Booking;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findById(Long id);
}
