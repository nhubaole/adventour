package com.adventour.web.controller;

import com.adventour.web.dto.*;
import com.adventour.web.enums.PaymentMethod;
import com.adventour.web.enums.StatusOfBooking;
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
    private final DashboardService dashboardService;
    private final TripService tripService;
    private final TourService tourService;

    @Autowired
    public LoginController(DashboardService dashboardService, TripService tripService, TourService tourService) {

        this.dashboardService = dashboardService;
        this.tripService = tripService;
        this.tourService = tourService;
    }

    @GetMapping("/")
    public String home(Model model){
//        TripDto tripDto = new TripDto();
//        TourDto tourDto = tourService.findByTourId(13L);
//
//        tripDto.setTourDto(tourDto);
//        tripDto.setStartDate(LocalDateTime.now());
//        tripDto.setEndDate(LocalDateTime.now().plusDays(4));
//
//        tripService.addNewTrip(tripDto);
//
//        List<TripDto> list = dashboardService.getListTripInMonth();
//        for(TripDto tripDto : list){
//            logger.info(tripDto.getCode());
//        }

//        List<Object[]> rs = dashboardService.getBookingAndTourNameIn7Days();
//        logger.info(String.valueOf(rs.size()));
//        for (Object[] row : rs){
//            long id = (long) row[0];
//            String nameTour  = (String) row[1];
//            int numberTickets = (int) row[2];
//            String typeTour = (String) row[3];
//            StatusOfBooking statusBooking=  (StatusOfBooking) row[4];
//            String status = statusBooking.name();
//            logger.info("id: {}, tourName: {}, numberPassenger: {}, typeTour: {}, statusBooking: {}",
//                    id, nameTour, numberTickets, typeTour, status);
//        }

        long revenue = dashboardService.getRevenueIn30Days();
        logger.info(String.valueOf(revenue));
        return "/index";
    }
}
