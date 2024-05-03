package com.adventour.web.controller;

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

    private CustomerService customerService;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    public LoginController( CustomerService customerService) {
        this.customerService = customerService;

    }

    @GetMapping("/")
    public String home(Model model){
        //save customer
//        CustomerDto customerDto = new CustomerDto();
//        customerDto.setId(2L);
//        customerDto.setNameCustomer("Nhi Huynh");
//        customerDto.setPhoneNumber("0364065040");
//        customerDto.setEmail("nhih0403@gmail.com");
//        customerDto.setDateOfBirth(LocalDate.of(2024, 4, 24));
//        customerDto.setCccd("123456789");
//        customerDto.setAddressCustomer("70 Võ Văn Ngân, Thủ Đức");
//        customerDto.setNationality("England");
//        if(customerService.updateCustomer(customerDto) == null) {
//            logger.info("tr ve null");
//        }
//        else {
//            List<CustomerDto> customers = customerService.getListCustomer();
//            logger.info("List of customers: {}" + customers);
//        }
//
//        CustomerDto customerDto1 = customerService.findById(2L);
//        logger.info(String.valueOf(customerDto1));

        List<CustomerDto> cus = customerService.searchCustomer("0914021632" );
        logger.info("List search customer"+ cus );

        return "/index";
    }
}
