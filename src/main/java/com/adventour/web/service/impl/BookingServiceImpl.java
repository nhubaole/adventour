package com.adventour.web.service.impl;

import com.adventour.web.dto.BookingDto;
import com.adventour.web.dto.CustomerDto;
import com.adventour.web.dto.TripDto;
import com.adventour.web.enums.StatusOfBooking;
import com.adventour.web.mapper.Mapper;
import com.adventour.web.models.*;
import com.adventour.web.repository.BookingRepository;
import com.adventour.web.repository.CustomerRepository;
import com.adventour.web.repository.TripRepository;
import com.adventour.web.service.BookingService;
import com.adventour.web.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final TripRepository tripRepository;
    private final CustomerRepository customerRepository;

    private final Mapper mapper;

    private final CustomerService customerService;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, TripRepository tripRepository, CustomerRepository customerRepository, Mapper mapper, CustomerService customerService) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.tripRepository = tripRepository;
        this.mapper = mapper;
        this.customerService = customerService;
    }


    @Override
    public List<BookingDto> getListBooking() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream().map((mapper::mapToBookingDto)).collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> findByBookingDateBetween(LocalDateTime start, LocalDateTime end) {
        List<Booking> bookings = bookingRepository.findByBookingDateBetween(start, end);
        List<BookingDto> bookingDtoList = new ArrayList<>();
        if (!bookings.isEmpty()) {
            for (Booking booking : bookings) {
                BookingDto bookingDto = mapper.mapToBookingDto(booking);
                bookingDtoList.add(bookingDto);
            }
        }
        return bookingDtoList;
    }

    @Override
    public BookingDto searchBooking() {
        return null;
    }

    @Override
    public Booking addNewBooking(BookingDto bookingDto) {
        if(validateBooking(bookingDto)){
            Booking booking = mapper.mapToBooking(bookingDto);

            //tinh tong tien
            int price = booking.getTrip().getPriceTicket();
            booking.setTotalAmount( (int)(booking.getNumberAdult() * price + booking.getNumberChildren() * price * 0.5));

            //customer moi
            if(bookingDto.getCustomerDto().getId() == null){
                CustomerDto customerDto = bookingDto.getCustomerDto();
                Customer customer = customerService.addNewCustomer(customerDto);
                booking.setCustomer(customer);
            }
            //TODO: luu danh sach passener, payment;
            return bookingRepository.save(booking);
        }
        return null;
    }

    @Override
    public BookingDto findById(Long id) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking != null) {
            return mapper.mapToBookingDto(booking);
        }
        return null;
    }

    @Override
    public Booking updateBooking(BookingDto bookingDto) {
        if(validateBooking(bookingDto)){
            Booking booking = mapper.mapToBooking(bookingDto);
            return bookingRepository.save(booking);
        }
        return null;
    }

    @Override
    public List<BookingDto> getBookingsByCustomerId(Long id) {
        List<BookingDto> result = new ArrayList<>();
        List<Booking> bookings = bookingRepository.findByCustomerId(id);
        if(!bookings.isEmpty()){
            for(Booking booking : bookings){
                BookingDto bookingDto = mapper.mapToBookingDto(booking);
                result.add(bookingDto);
            }
        }
        return result;
    }

    @Override
    public List<BookingDto> getBookingsByTripId(Long id) {
        List<BookingDto> result = new ArrayList<>();
        List<Booking> bookings = bookingRepository.findByTripId(id);
        if(!bookings.isEmpty()){
            for(Booking booking : bookings){
                BookingDto bookingDto = mapper.mapToBookingDto(booking);
                result.add(bookingDto);
            }
        }
        return result;
    }

    @Override
    public boolean validateBooking(BookingDto bookingDto) {
        if(bookingDto.getTripDto() != null
        && bookingDto.getCustomerDto() !=null
        && bookingDto.getBookingDate() != null
        && bookingDto.getNumberChildren() >= 0
        && bookingDto.getNumberBaby() >= 0
        && bookingDto.getNumberAdult() >= 0){
            return true;
        }

        return false;
    }

    @Override
    public void genarateTickets() {


    }



}
