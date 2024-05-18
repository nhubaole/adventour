package com.adventour.web.service;

import com.adventour.web.dto.BookingDto;
import com.adventour.web.dto.PassengerDto;
import com.adventour.web.dto.PaymentInformationDto;
import com.adventour.web.models.Booking;
import com.adventour.web.models.Passenger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface BookingService {
    List<BookingDto> getListBooking();
    List<BookingDto> findByBookingDateBetween(LocalDateTime start, LocalDateTime end);

    BookingDto searchBooking();

    Booking addNewBooking(BookingDto bookingDto);

    BookingDto findById(Long id);

    Booking updateBooking(BookingDto bookingDto);

    List<BookingDto> getBookingsByCustomerId( Long id);
    List<BookingDto> getBookingsByTripId(Long id);

    Set<PassengerDto> getPassengerOfBooking (Long  idBooking);

    Set<PaymentInformationDto> getPaymentOfBooking( Long idBooking);

    boolean validateBooking(BookingDto bookingDto);

    void genarateTickets ();

}
