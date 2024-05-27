package com.adventour.web.controller;

import com.adventour.web.dto.TripDto;
import com.adventour.web.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TripController {
    private TripService tripService;

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

    @GetMapping("/train-info")
    public String trainInfo(Model model){return "/pages/train-info";}

    @GetMapping("/add-new-trip")
    public String addNewTrip(Model model){return "/pages/add-new-trip";}

}
