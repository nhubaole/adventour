package com.adventour.web.controller;

import com.adventour.web.dto.*;
import com.adventour.web.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TripController {
    private TripService tripService;
    static TripDto addNewTrip;
        @Autowired
        public TripController(TripService tripService) {
            this.tripService = tripService;
        }
    @GetMapping("/alltrip")
    public String AllTrip(Model model){
        List<TripDto> tripDtos = tripService.getListTrip();
        model.addAttribute("trips", tripDtos);
        return "/pages/all-trip";
    }

    @GetMapping("/information/{id}")
    public String informationTrain(@PathVariable("id") long id, Model model){
        TripDto tripDto = tripService.getTripDetail(id);
        model.addAttribute("trip", tripDto);
        return "/pages/trip-information";
        }

    @GetMapping("/train-info/{id}")
    public String trainInfo(@PathVariable("id") long id, Model model){
        TripDto tripDto = tripService.getTripDetail(id);
        model.addAttribute("trip", tripDto);
            return "/pages/train-info";
        }

    @GetMapping("/add-new-trip")
    public String addNewTrip(Model model){
            TripDto tripDto = new TripDto();
            model.addAttribute("trip", tripDto);
            return "/pages/add-new-trip";
        }

    @PostMapping("/add-new-trip")
    public String saveTrip(@ModelAttribute("trip") TripDto tripDto){
            tripService.addNewTrip(tripDto);
            return "redirect:/alltrip";
    }
}

