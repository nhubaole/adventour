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
        List<Object[]> rs = dashboardService.getRecentPayment();
        for (Object[] row : rs){
            PaymentInformation paymentInformation = (PaymentInformation) row[0];
            String nameCus = (String) row[1];
            logger.info("Payment ID: {}, Amount: {}, Payment Time: {}, Customer Name: {}",
                    paymentInformation.getId(), paymentInformation.getAmountOfMoney(),
                    paymentInformation.getPaymentTime(), nameCus);
        }

        return "/index";
    }
}
