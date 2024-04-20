package com.adventour.web.controller;

import com.adventour.web.dto.TourDto;
import com.adventour.web.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TourController {
    private TourService tourService;

    @Autowired
    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping("/tours")
    public String listClubs(Model model){
        List<TourDto> tourDtos = tourService.findAllTours();
        model.addAttribute("tours", tourDtos);
        return "tour-list";
    }

    @GetMapping("/")
    public String home(Model model){
        return "/index";
    }

    @GetMapping("/all-tour")
    public String allTour(Model model){
        return "/pages/all-tour";
    }
    @GetMapping("/ticket-detail")
    public String viewTicketDetail(Model model){
        return "/pages/ticket-detail";
    }

    @GetMapping("/booking-detail-ticket")
    public String viewBookingDetailTicket(Model model){
        return "/pages/booking-detail-ticket";
    }

    //===========================================================
    @GetMapping("/dashboard")
    public String dashboard(Model model){
        return  "/pages/dashboard";
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

    @GetMapping("/statistic")
    public String statistic(Model model){return "/pages/statistic";}

    @GetMapping("/alltrip")
    public String AllTrip(Model model){return "/pages/all-trip";}

    @GetMapping("/information")
    public String information(Model model){return "/pages/trip-information";}

    @GetMapping("/train-info")
    public String trainInfo(Model model){return "/pages/train-info";}

    @GetMapping("/add-new-trip")
    public String addNewTrip(Model model){return "/pages/add-new-trip";}


    @GetMapping("/tour-info")
    public String tourInfo(Model model) {return "/pages/tour-info";}

    @GetMapping("/tour-maps")
    public String tourMap(Model model){return "/pages/tour-map";}

    @GetMapping("/add-tour")
    public String addNewTour(Model model){return "/pages/add-tour";}

    @GetMapping("/tour-detail")
    public String tourDetail(Model model){return "/pages/tour-detail";}

    @GetMapping("/all-place")
    public String allPlace(Model model){return "/pages/all-place";}

    @GetMapping("/place-detail")
    public String placeDetail(Model model){return "/pages/place-detail";}

    @GetMapping("/add-new-place")
    public String addPlace(Model model){return "/pages/add-new-place";}

    @GetMapping("/add-schedule")
    public String addSchedule(Model model){return "/pages/add-Schedule";}

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

    @GetMapping("/add-new-booking-payment")
    public String addNewBookingPayment(Model model){return "/pages/add-new-booking-payment";}
}
