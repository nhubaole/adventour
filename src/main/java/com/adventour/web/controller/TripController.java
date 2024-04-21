package com.adventour.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TripController {
//    private TripService tripService;

    //    @Autowired
//    public TripController(TripService tripService) {
//        this.tripService = tripService;
//    }
    @Autowired
    public TripController( ) {

    }
    @GetMapping("/alltrip")
    public String AllTrip(Model model){return "/pages/all-trip";}

    @GetMapping("/information")
    public String information(Model model){return "/pages/trip-information";}

    @GetMapping("/train-info")
    public String trainInfo(Model model){return "/pages/train-info";}

    @GetMapping("/add-new-trip")
    public String addNewTrip(Model model){return "/pages/add-new-trip";}

}
