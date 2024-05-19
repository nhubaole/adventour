package com.adventour.web.controller;

import com.adventour.web.dto.CustomerDto;
import com.adventour.web.models.Customer;
import com.adventour.web.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class CustomerController {
    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customer")
    public String customer(Model model){
        return  "/pages/customer";
    }

    @GetMapping("/add-new-customer")
    public String addNewCustomer(Model model){
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "/pages/add-new-customer";
    }

    @PostMapping("/add-new-customer")
    public String saveCustomer(@ModelAttribute("customer") CustomerDto customer){
        customerService.addNewCustomer(customer);
        return "/pages/customer";
    }


    @GetMapping("/profile")
    public String profile(Model model){return "/pages/profile";}

    @GetMapping("/booking")
    public String booking(Model model){return "/pages/booking-bill";}
}
