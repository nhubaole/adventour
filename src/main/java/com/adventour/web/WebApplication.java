package com.adventour.web;

import com.adventour.web.controller.TourController;
import com.adventour.web.dto.BookingDto;
import com.adventour.web.dto.CustomerDto;
import com.adventour.web.models.Customer;
import com.adventour.web.repository.CustomerRepository;
import com.adventour.web.service.CustomerService;
import com.adventour.web.service.TourService;
import com.adventour.web.service.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class  WebApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
}
