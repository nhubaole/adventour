package com.adventour.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookingController {
//    private BookingService bookingService;

    //    @Autowired
//    public BookingController(BookingService bookingService) {
//        this.bookingService = bookingService;
//    }
    @Autowired
    public BookingController( ) {

    }
    @GetMapping("/all-booking")
    public String allBooking(Model model){return "/pages/all-booking";}

    @GetMapping("/booking-detail-information")
    public String bookingInfo(Model model){return "/pages/booking-detail-information";}

    @GetMapping("/add-new-booking-information")
    public String addNewBookingInfo(Model model){return "/pages/add-new-booking-information";}

    @GetMapping("/booking-detail-passenger")
    public String bookingDetailPassenger(Model model){return "/pages/booking-detail-passenger";}

    @GetMapping("/add-new-booking-passenger")
    public String addNewBookingPassenger(Model model){return "/pages/add-new-booking-passenger";}

    @GetMapping("/booking-detail-payment")
    public String bookingDetailPayment(Model model){return "/pages/booking-detail-payment";}
    @GetMapping("/booking-detail-ticket")
    public String viewBookingDetailTicket(Model model){
        return "/pages/booking-detail-ticket";
    }

    @GetMapping("/add-new-booking-payment")
    public String addNewBookingPayment(Model model){return "/pages/add-new-booking-payment";}
}
