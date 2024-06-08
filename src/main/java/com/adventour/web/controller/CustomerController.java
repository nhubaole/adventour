package com.adventour.web.controller;


import com.adventour.web.dto.BookingDto;
import com.adventour.web.dto.CustomerDto;
import com.adventour.web.dto.PaymentInformationDto;
import com.adventour.web.models.Customer;
import com.adventour.web.service.BookingService;
import com.adventour.web.service.BucketService;
import com.adventour.web.service.CustomerService;
import com.adventour.web.utils.FormatNumber;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import java.util.ArrayList;
import java.util.List;


@Controller
public class CustomerController {
    private CustomerService customerService;
    private BookingService bookingService;
    private BucketService bucketService;

    private static Logger logger = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    public CustomerController(CustomerService customerService,
                              BookingService bookingService,
                              BucketService bucketService) {

        this.customerService = customerService;
        this.bookingService = bookingService;
        this.bucketService = bucketService;
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

    @GetMapping("/customer/{customerId}/edit")
    public String editCustomer(@PathVariable("customerId") long customerId, Model model){
        CustomerDto customerDto = customerService.findById(customerId);
        model.addAttribute("customer", customerDto);
        return "/pages/edit-customer";
    }

    @PostMapping("/customer/{customerId}/edit")
    public String saveEditCustomer(@PathVariable("customerId") long id, @ModelAttribute("customer") CustomerDto customer){
        customer.setId(id);
        customerService.updateCustomer(customer);
        return "redirect:/customer";
    }




    @GetMapping ("/customer/{customerId}/delete")
    public String deleteCustomer(@PathVariable("customerId") long customerId) {
        CustomerDto customerDto = customerService.findById(customerId);
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
    public String saveCustomer(@ModelAttribute("customer") CustomerDto customer,
                               @RequestParam("files") MultipartFile[] files){
        logger.info("Number of images: " + files.length);
        if(files.length == 1 && files[0].getOriginalFilename().isEmpty()){

        }
        else {
            List<String> placeImages = new ArrayList<>();
            for (MultipartFile image : files){
                try{
                    String fileUrl = bucketService.uploadFile(image);
                    placeImages.add(fileUrl);
                } catch (Exception e){
                    logger.error("Error uploading image: " + e.getMessage());
                }
            }
            customer.setImagesCustomer(placeImages.toArray(new String[0]));
        }
        logger.info(customer.getIsMale() + "==============================");

        customerService.addNewCustomer(customer);
        return "redirect:/customer";
    }

    @GetMapping ("/booking/{id}")
    public String bookingCustomer(@PathVariable("id") long customerId, Model model){
        CustomerDto customerDto = customerService.findById(customerId);
        List<BookingDto> bookingDtos = bookingService.getBookingsByCustomerId(customerId);
        model.addAttribute("customer", customerDto);

        model.addAttribute("bookings", bookingDtos);
        model.addAttribute("formatNumber", new FormatNumber());
        return "/pages/booking-bill";
    }

}
