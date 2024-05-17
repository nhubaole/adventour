package com.adventour.web.controller;

import com.adventour.web.dto.*;
import com.adventour.web.models.Customer;
import com.adventour.web.models.Location;
import com.adventour.web.models.Tour;
import com.adventour.web.models.Trip;
import com.adventour.web.service.LocationService;
import com.adventour.web.service.TourService;
import com.adventour.web.service.TripService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adventour.web.dto.CustomerDto;
import com.adventour.web.service.CustomerService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final TripService tripService;
    private  final  TourService tourService;

    private  final CustomerService customerService;
    @Autowired
    public LoginController(TripService tripService, TourService tourService, CustomerService customerService) {
        this.tripService = tripService;
        this.tourService = tourService;
        this.customerService = customerService;
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
//        TripDto tripDto = tripService.getTripDetail(1L);
//        logger.info(tripDto.toString());

//        tripDto.setEndDate(endDate);

        //Trip trip = tripService.addNewTrip(tripDto);
        logger.info(tripService.getTripByIdTour(1L).toString());

        return "/index";
    }
}
