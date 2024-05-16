package com.adventour.web.service.impl;

import com.adventour.web.dto.BookingDto;
import com.adventour.web.dto.CustomerDto;
import com.adventour.web.dto.TripDto;
import com.adventour.web.enums.StatusOfBooking;
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

    private CustomerService customerService;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, TripRepository tripRepository, CustomerRepository customerRepository, CustomerService customerService) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.tripRepository = tripRepository;
        this.customerService = customerService;
    }


    @Override
    public List<BookingDto> getListBooking() {
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
    public BookingDto searchBooking() {
        return null;
    }

    @Override
    public Booking addNewBooking(BookingDto bookingDto, long idTrip, long idCustomer) {
        if(validateBooking(bookingDto)){
            Booking booking = mapToBooking(bookingDto);
            //tính so nguoi
            booking.setNumberOfPassengers(booking.getNumberBaby() + booking.getNumberAdult() + booking.getNumberChildren());

            //tinh tien
            int price = booking.getTrip().getPriceTicket();
            booking.setTotalAmount( (int)(booking.getNumberAdult() *price + booking.getNumberChildren() * price * 0.5));

            Trip trip = tripRepository.findById(idTrip).orElse(null);
            booking.setTrip(trip);

            Customer customer = customerRepository.findById(idCustomer).orElse(null);
            booking.setCustomer(customer);

            //set trang thai
            //TODO: update logic
//            if(booking.getTotalAmount() <= booking.getAmountPaid()){
//                booking.setStatus(StatusOfBooking.COMPLETED);
//            } else {
//                booking.setStatus(StatusOfBooking.PENDING);
//            }

            return bookingRepository.save(booking);
        }
        return null;
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
    public Booking updateBooking(BookingDto bookingDto) {
        if(validateBooking(bookingDto)){
            return bookingRepository.save(mapToBooking(bookingDto));
        }
        return null;
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

    @Override
    public List<BookingDto> getBookingsByTripId(Long id) {
        List<BookingDto> result = new ArrayList<>();
        List<Booking> bookings = bookingRepository.findByTripId(id);
        if(!bookings.isEmpty()){
            for(Booking booking : bookings){
                result.add(mapToBookingDto(booking));
            }
        }
        return result;
    }

    @Override
    public boolean validateBooking(BookingDto bookingDto) {
        if(bookingDto.getTrip() != null
        && bookingDto.getCustomer() !=null
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

    private Booking mapToBooking(BookingDto bookingDto) {
        Booking booking = new Booking();
        booking.setId(bookingDto.getId());
        booking.setBookingDate(bookingDto.getBookingDate());
        booking.setStatus(bookingDto.getStatus());
        booking.setNumberOfPassengers(bookingDto.getNumberOfPassengers());
        booking.setTotalAmount(bookingDto.getTotalAmount());
        booking.setTrip(bookingDto.getTrip());
        booking.setCustomer(bookingDto.getCustomer());

        //con ds passenger ???
        return booking;
    }

    private BookingDto mapToBookingDto(Booking booking) {

        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setBookingDate(booking.getBookingDate());
        bookingDto.setStatus(booking.getStatus());
        bookingDto.setNumberOfPassengers(booking.getNumberOfPassengers());
        bookingDto.setTotalAmount(booking.getTotalAmount());
        bookingDto.setTrip(booking.getTrip());
        bookingDto.setCustomer(booking.getCustomer());

        return bookingDto;
    }

}
