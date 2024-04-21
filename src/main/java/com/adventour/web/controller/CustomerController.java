package com.adventour.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {
//    private CustomerService customerService;

//    @Autowired
//    public CustomerController(CustomerService customerService) {
//        this.customerService = customerService;
//    }
    @Autowired
    public CustomerController( ) {

    }

    @GetMapping("/customer")
    public String customer(Model model){
        return  "/pages/customer";
    }

    @GetMapping("/add-new-customer")
    public String addNewCustomer(Model model){ return "/pages/add-new-customer";}

    @GetMapping("/profile")
    public String profile(Model model){return "/pages/profile";}

    @GetMapping("/booking")
    public String booking(Model model){return "/pages/booking-bill";}
}
