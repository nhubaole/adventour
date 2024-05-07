package com.adventour.web.controller;

import com.adventour.web.dto.LocationDto;
import com.adventour.web.models.Location;
import com.adventour.web.service.LocationService;
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
import java.util.List;

@Controller
public class LoginController {


    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    public LoginController( ) {
    }

    @GetMapping("/")
    public String home(Model model){
        return "/index";
    }
}
