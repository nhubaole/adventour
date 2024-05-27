package com.adventour.web.controller;

import com.adventour.web.dto.CustomerDto;
import com.adventour.web.dto.PaymentInformationDto;
import com.adventour.web.models.Customer;
import com.adventour.web.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
public class CustomerController {
    private CustomerService customerService;

    static CustomerDto currentAddingCustomer;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customer")
    public String customer(Model model, @Param("keyword") String keyword){
        List<CustomerDto> customerDtos = new ArrayList<>();
        if (keyword == null) {
            customerDtos =  customerService.getListCustomer();
        } else {
            customerDtos = customerService.searchCustomer(keyword);
            model.addAttribute("keyword", keyword);
        }
        model.addAttribute("customers", customerDtos);
        return  "/pages/customer";
    }

    @GetMapping("/customer/{customerId}")
    public String profileCustomer(@PathVariable("customerId") long customerId, Model model){
        CustomerDto customerDto = customerService.findById(customerId);
        model.addAttribute("customer", customerDto);
        return "/pages/profile";
    }

//    @GetMapping("/customer/{customerId}/edit")
    @GetMapping ("/customer/{customerId}/delete")
    public String deleteCustomer(@PathVariable("customerDto") CustomerDto customerDto){
        customerService.deleteCustomer(customerDto);
        return "redirect:/customer";
    }
    @GetMapping("/add-new-customer")
    public String addNewCustomer(Model model){
        CustomerDto customer = new CustomerDto();
        model.addAttribute("customer", customer);
        return "/pages/add-new-customer";
    }

    @PostMapping("/add-new-customer")
    public String saveCustomer(@ModelAttribute("customer") CustomerDto customer){
        customerService.addNewCustomer(customer);
        return "redirect:/customer";
    }

    @GetMapping ("/booking/{customerId}")
    public String bookingCustomer(@PathVariable("customerId") long customerId, Model model){
        CustomerDto customerDto = customerService.findById(customerId);
        model.addAttribute("customer", customerDto);
        return "/pages/booking-bill";
    }
}
