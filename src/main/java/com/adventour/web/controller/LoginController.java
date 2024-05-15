package com.adventour.web.controller;

import com.adventour.web.dto.LocationDto;
import com.adventour.web.dto.TourDto;
import com.adventour.web.dto.TripDto;
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
    private  final TripService tripService;
    private  final TourService tourService;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    public LoginController(TripService tripService, TourService tourService) {
        this.tripService = tripService;
        this.tourService = tourService;
    }

    @GetMapping("/")
    public String home(Model model){
        TripDto tripDto = new TripDto();
        Tour tour = tourService.findByJustTourId(1L);
        tripDto.setId(14L);
        tripDto.setTour(tour);
        tripDto.setTypeOfTrip("Thong Thuong");
        tripDto.setSlots(60);
        tripDto.setMinPassenger(10);
        tripDto.setDiscount(0);

        LocalDateTime statDate = LocalDateTime.of(2024, 5, 15, 15, 30, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 5, 14, 15, 30, 0);

        tripDto.setStartDate(statDate);
        tripDto.setEndDate(endDate);

        Trip trip = tripService.editTrip(tripDto);
        logger.info(trip.toString());

//        List<TripDto> trips =tripService.getListTrip();
//        for(TripDto trip : trips){
//            logger.info(trip.toString());
//        }

        logger.info(tripService.getTripDetail(14L).toString());

        return "/index";
    }
}
