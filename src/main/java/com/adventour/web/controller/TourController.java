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
}
