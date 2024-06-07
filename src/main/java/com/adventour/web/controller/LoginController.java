package com.adventour.web.controller;

import com.adventour.web.dto.*;
import com.adventour.web.enums.PaymentMethod;
import com.adventour.web.enums.StatusOfBooking;
import com.adventour.web.models.*;
import com.adventour.web.repository.TourRepository;
import com.adventour.web.repository.TripRepository;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private  final  CustomerService customerService;

    private final TripService tripService;

    @Autowired
    public LoginController(CustomerService customerService, TripService tripService) {
        this.customerService = customerService;
        this.tripService = tripService;
    }

    @GetMapping("/")
    public String home(Model model){
        TripDto tripDto = tripService.getTripDetail(3L);
        List<PassengerDto> passengerDtos = tripService.getTripPassenger(tripDto);
        logger.info(String.valueOf(passengerDtos.size()));
        for (PassengerDto passengerDto : passengerDtos){
            logger.info(String.valueOf(passengerDto.getId()));
        }
        return "/index";
    }
}
