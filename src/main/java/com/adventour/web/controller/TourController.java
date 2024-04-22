package com.adventour.web.controller;

import com.adventour.web.dto.TourDto;
import com.adventour.web.models.Tour;
import com.adventour.web.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TourController {
    private TourService tourService;

    @Autowired
    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping("/all-tour")
    public String allTour(Model model){
        List<TourDto> tourDtos = tourService.findAllTours();
        model.addAttribute("tours", tourDtos);
        return "/pages/all-tour";
    }

    @PostMapping("/add-tour")
    public String saveTour(@ModelAttribute("tour") Tour tour){
        tourService.saveTour(tour);
        return "redirect:/all-tour";
    }

    @GetMapping("/all-tour/{tourId}")
    public String tourDetail(@PathVariable("tourId") long tourId, Model model){
        TourDto tourDto = tourService.findByTourId(tourId);
        model.addAttribute("tour", tourDto);
        return "/pages/tour-info";
    }

    @GetMapping("/tour-maps")
    public String tourMap(Model model){return "/pages/tour-map";}

    @GetMapping("/add-tour")
    public String addNewTour(Model model){
        Tour tour = new Tour();
        model.addAttribute("tour", tour);
        return "/pages/add-tour";
    }

    @GetMapping("/tour-detail")
    public String tourDetail(Model model){return "/pages/tour-detail";}

    @GetMapping("/add-schedule")
    public String addSchedule(Model model){return "/pages/add-Schedule";}
}
