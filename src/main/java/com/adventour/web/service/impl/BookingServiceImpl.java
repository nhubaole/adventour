package com.adventour.web.service.impl;

import com.adventour.web.dto.BookingDto;
import com.adventour.web.dto.TripDto;
import com.adventour.web.models.Booking;
import com.adventour.web.models.Customer;
import com.adventour.web.models.Trip;
import com.adventour.web.repository.BookingRepository;
import com.adventour.web.repository.CustomerRepository;
import com.adventour.web.repository.TripRepository;
import com.adventour.web.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookingServiceImpl implements BookingService {

    private BookingRepository bookingRepository;
    private TripRepository tripRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, TripRepository tripRepository, CustomerRepository customerRepository) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.tripRepository = tripRepository;
    }



    @Override
    public List<BookingDto> findAllBooking() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream().map((booking -> mapToBookingDto(booking))).collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> findByBookingDateBetween(LocalDateTime start, LocalDateTime end) {
        List<Booking> bookings = bookingRepository.findByBookingDateBetween(start, end);
        List<BookingDto> bookingDtoList = new ArrayList<>();
        if (!bookings.isEmpty()) {
            for (Booking booking : bookings) {
                BookingDto bookingDto = mapToBookingDto(booking);
                bookingDtoList.add(bookingDto);
            }
        }
        return bookingDtoList;
    }

    @Override
    public Booking saveBooking(BookingDto bookingDto) {
       if(allInforIsCanSave(bookingDto)){
           Booking booking = mapToBooking(bookingDto);
           return bookingRepository.save(booking);
       }
        return null;

    }

    private boolean allInforIsCanSave(BookingDto bookingDto) {
        if(StringUtils.isEmpty(bookingDto.getIdCustomer().toString())
            || StringUtils.isEmpty(bookingDto.getIdTrip().toString())
            || bookingDto.getBookingDate()== null
            || bookingDto.getTotalAmount() <=0
            ||bookingDto.getNumberOfPassengers() <=0){
            return false;
        }
        return true;
    }

    @Override
    public BookingDto findById(Long id) {

        Booking booking = bookingRepository.findById(id).orElse(null);

        if (booking != null) {
            return mapToBookingDto(booking);
        }
        return null;
    }

    @Override
    public void updateBooking(BookingDto bookingDto) {
        Booking booking = mapToBooking(bookingDto);
        bookingRepository.save(booking);
    }

    @Override
    public List<BookingDto> getBookingsByCustomerId(Long id) {
        List<BookingDto> result = new ArrayList<>();
        List<Booking> bookings = bookingRepository.findByCustomerId(id);
        if(!bookings.isEmpty()){
            for(Booking booking : bookings){
                result.add(mapToBookingDto(booking));
            }
        }
        return result;
    }

    private Booking mapToBooking(BookingDto bookingDto) {
        Booking booking = new Booking();
//        booking.setId(bookingDto.getId());
//        booking.setBookingDate(bookingDto.getBookingDate());
//        booking.setStatus(bookingDto.getStatus());
//        booking.setImagesUrl(bookingDto.getImagesUrl());
//        booking.setNumberOfPassengers(bookingDto.getNumberOfPassengers());
//        booking.setTotalAmount(bookingDto.getTotalAmount());
//        booking.setAmountPaid(bookingDto.getAmountPaid());
//
//        Trip trip = tripRepository.findById(bookingDto.getIdTrip()).orElse(null);
//        Customer customer = customerRepository.findById(bookingDto.getIdCustomer()).orElse(null);
//
//        booking.setTrip(trip);
//        booking.setCustomer(customer);

        //con ds passenger ???
        return booking;
    }

    private BookingDto mapToBookingDto(Booking booking) {

        BookingDto bookingDto = new BookingDto();
//        bookingDto.setId(booking.getId());
//        bookingDto.setBookingDate(booking.getBookingDate());
//        bookingDto.setImagesUrl(booking.getImagesUrl());
//        bookingDto.setStatus(booking.getStatus());
//        bookingDto.setNumberOfPassengers(booking.getNumberOfPassengers());
//        bookingDto.setTotalAmount(booking.getTotalAmount());
//        bookingDto.setAmountPaid(booking.getAmountPaid());
//        bookingDto.setIdCustomer(booking.getCustomer().getId());
//        bookingDto.setIdTrip(booking.getTrip().getId());

        return bookingDto;
    }

}
