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
    private  final  TripService tripService;
    private final CustomerService customerService;
    private final BookingService bookingService;

    @Autowired
    public LoginController(TripService tripService, CustomerService customerService, BookingService bookingService) {
        this.tripService = tripService;
        this.customerService = customerService;
        this.bookingService = bookingService;
    }

    @GetMapping("/")
    public String home(Model model){
        logger.info(bookingService.findById(17L).toString());
        return "/index";
    }
}
