package com.adventour.web.service;

import com.adventour.web.dto.BookingDto;
import com.adventour.web.models.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    List<BookingDto> getListBooking();
    List<BookingDto> findByBookingDateBetween(LocalDateTime start, LocalDateTime end);

    BookingDto searchBooking();

    Booking addNewBooking(BookingDto bookingDto);

    BookingDto findById(Long id);

    Booking updateBooking(BookingDto bookingDto);

    List<BookingDto> getBookingsByCustomerId( Long id);
    List<BookingDto> getBookingsByTripId( Long id);

    boolean validateBooking(BookingDto bookingDto);

    void genarateTickets ();



}
