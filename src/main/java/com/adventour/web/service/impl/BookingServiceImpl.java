package com.adventour.web.service.impl;

import com.adventour.web.dto.BookingDto;
import com.adventour.web.models.Booking;
import com.adventour.web.repository.BookingRepository;
import com.adventour.web.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;

public class BookingServiceImpl implements BookingService {

    private BookingRepository bookingRepository;

    @Autowired
    public  BookingServiceImpl (BookingRepository bookingRepository){
        this.bookingRepository = bookingRepository;
    }

    private BookingDto mapToBookingDto(Booking booking){
        return BookingDto.builder()
                .id(booking.getId())
                .bookingDate(booking.getBookingDate())
                .imagesUrl(booking.getImagesUrl())
                .status(booking.getStatus())
                .numberOfPassengers(booking.getNumberOfPassengers())
                .totalAmount(booking.getTotalAmount())
                .amountPaid(booking.getAmountPaid())
                .build();
    }
}
