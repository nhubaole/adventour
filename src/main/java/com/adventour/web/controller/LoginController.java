package com.adventour.web.controller;

import com.adventour.web.dto.*;
import com.adventour.web.enums.PaymentMethod;
import com.adventour.web.models.*;
import com.adventour.web.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adventour.web.dto.CustomerDto;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final TripService tripService;
    private  final  TourService tourService;

    private  final CustomerService customerService;

    private final BookingService bookingService;
    @Autowired
    public LoginController(TripService tripService, TourService tourService, CustomerService customerService, BookingService bookingService) {
        this.tripService = tripService;
        this.tourService = tourService;
        this.customerService = customerService;
        this.bookingService = bookingService;
    }

    @GetMapping("/")
    public String home(Model model){

//        TourDto tourDto = tourService.findByTourId(1L);
//
//        TripDto tripDto = new TripDto();
//
//        //Tour tour = tourService.findByJustTourId(1L);
//
//        tripDto.setTourDto(tourDto);
//        tripDto.setSlots(15);
//        tripDto.setDiscount(20);
//
//        LocalDateTime statDate = LocalDateTime.of(2024, 5, 15, 15, 30, 0);
//        LocalDateTime endDate = LocalDateTime.of(2024, 5, 16, 15, 30, 0);
//        tripDto.setStartDate(statDate);
//        tripDto.setEndDate(endDate);

        //LocalDateTime endDate = LocalDateTime.of(2024, 5, 19, 15, 30, 0);
//          TripDto tripDto = tripService.getTripDetail(2L);
//        logger.info(tripDto.toString());

//        tripDto.setEndDate(endDate);

//        Trip trip = tripService.addNewTrip(tripDto);

        TripDto tripDto = tripService.getTripDetail(2L);


        CustomerDto customerDto = new CustomerDto();
        customerDto.setNameCustomer("Huỳnh Ngoc Y");
        customerDto.setAddressCustomer("Dh CNTT");
        customerDto.setCccd("06666784345");
        customerDto.setIsMale(true);
        customerDto.setEmail("nhih0406@gmail.com");
        customerDto.setNationality("England");

        LocalDate dob = LocalDate.of(2004, 4, 12);
        customerDto.setDateOfBirth(dob);
        customerDto.setPhoneNumber("0364566089");


        BookingDto bookingDto = new BookingDto();

        LocalDateTime timeBooking = LocalDateTime.now();
        bookingDto.setBookingDate(timeBooking);

        bookingDto.setNumberBaby(1);
        bookingDto.setNumberAdult(14);
        bookingDto.setNumberChildren(4);

        bookingDto.setTripDto(tripDto);
        bookingDto.setCustomerDto(customerDto);


        Set<PassengerDto> passengerDtos = new HashSet<>();

        passengerDtos.add(new PassengerDto("Nguyen Van F", "001", true, "Adult", LocalDate.of(1980, 1, 1)));
        passengerDtos.add(new PassengerDto("Tran Thi G", "002", false, "Adult", LocalDate.of(1985, 2, 2)));
        passengerDtos.add(new PassengerDto("Le Van H", "003", true, "Child", LocalDate.of(2010, 3, 3)));
        passengerDtos.add(new PassengerDto( "Pham Thi I", "004", false, "Child", LocalDate.of(2012, 4, 4)));
        passengerDtos.add(new PassengerDto( "Hoang Van K", "005", true, "Adult", LocalDate.of(1990, 5, 5)));


        bookingDto.setPassengerDtos(passengerDtos);
        // Print the passengers set

        Set<PaymentInformationDto> paymentInformationDtoSet = new HashSet<>();
        PaymentInformationDto paymentInformationDto = new PaymentInformationDto();

        paymentInformationDto.setPaymentMethod(PaymentMethod.BANKTRANSFER);
        paymentInformationDto.setPaymentTime(LocalDateTime.now());
        paymentInformationDto.setAmountOfMoney(500000);
        paymentInformationDtoSet.add(paymentInformationDto);

        bookingDto.setPaymentInformationDtos(paymentInformationDtoSet);
       logger.info(bookingService.addNewBooking(bookingDto).toString());

//        List<BookingDto>  bookingDto = bookingService.findById(16L);
//
//        for(BookingDto pass: bookingDto){
//            logger.info(pass.toString());
//        }
//
//        logger.info(bookingService.findById(17L).toString());
        return "/index";
    }
}
