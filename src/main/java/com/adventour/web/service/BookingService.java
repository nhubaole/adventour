package com.adventour.web.service;

import com.adventour.web.dto.BookingDto;
import com.adventour.web.models.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    List<BookingDto> findAllBooking();
    List<BookingDto> findByBookingDateBetween(LocalDateTime start, LocalDateTime end);

    Booking saveBooking(BookingDto bookingDto);

    BookingDto findById(Long id);

    void updateBooking(BookingDto bookingDto);

    List<BookingDto> getBookingsByCustomerId( Long id);

}
