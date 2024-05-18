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

        //Trip trip = tripService.addNewTrip(tripDto);

//        TripDto tripDto = tripService.getTripDetail(2L);
//
//
//        CustomerDto customerDto = customerService.findById(1L);
//
//        BookingDto bookingDto = new BookingDto();
//
//        LocalDateTime timeBooking = LocalDateTime.now();
//        bookingDto.setBookingDate(timeBooking);
//
//        bookingDto.setNumberBaby(2);
//        bookingDto.setNumberAdult(10);
//        bookingDto.setNumberChildren(4);
//
//        bookingDto.setTripDto(tripDto);
//        bookingDto.setCustomerDto(customerDto);
//
//
//        Set<PassengerDto> passengerDtos = new HashSet<>();
//
//        passengerDtos.add(new PassengerDto("Nguyen Van A", "001", true, "Adult", LocalDate.of(1980, 1, 1)));
//        passengerDtos.add(new PassengerDto("Tran Thi B", "002", false, "Adult", LocalDate.of(1985, 2, 2)));
//        passengerDtos.add(new PassengerDto("Le Van C", "003", true, "Child", LocalDate.of(2010, 3, 3)));
//        passengerDtos.add(new PassengerDto( "Pham Thi D", "004", false, "Child", LocalDate.of(2012, 4, 4)));
//        passengerDtos.add(new PassengerDto( "Hoang Van E", "005", true, "Adult", LocalDate.of(1990, 5, 5)));
//
//
//        bookingDto.setPassengerDtos(passengerDtos);
//        // Print the passengers set
//
//        Set<PaymentInformationDto> paymentInformationDtoSet = new HashSet<>();
//        PaymentInformationDto paymentInformationDto = new PaymentInformationDto();
//
//        paymentInformationDto.setPaymentMethod(PaymentMethod.BANKTRANSFER);
//        paymentInformationDto.setPaymentTime(LocalDateTime.now());
//        paymentInformationDto.setAmountOfMoney(2000000);
//        paymentInformationDtoSet.add(paymentInformationDto);
//
//        bookingDto.setPaymentInformationDtos(paymentInformationDtoSet);
//
//        logger.info(bookingService.addNewBooking(bookingDto).toString());

        logger.info(bookingService.getListBooking().toString());

        return "/index";
    }
}
