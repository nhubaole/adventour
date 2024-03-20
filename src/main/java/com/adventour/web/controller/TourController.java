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
        return "/pages/add-new-tour";
    }
}
