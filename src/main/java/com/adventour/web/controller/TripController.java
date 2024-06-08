package com.adventour.web.controller;

import com.adventour.web.dto.*;
import com.adventour.web.service.*;
import com.adventour.web.dto.TripDto;
import com.adventour.web.service.TripService;
import com.adventour.web.utils.FormatNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TripController {
    private TripService tripService;

    private TourService tourService;

    private PassengerService passengerService;

    private BookingService bookingService;

    private CustomerService customerService;
    private static Logger logger = LoggerFactory.getLogger(TripController.class);
    static TripDto addNewTrip;
        @Autowired
        public TripController(TripService tripService,
                              TourService tourService,
                              PassengerService passengerService,
                              BookingService bookingService,
                              CustomerService customerService) {
            this.tripService = tripService;
            this.tourService = tourService;
            this.passengerService = passengerService;
            this.bookingService = bookingService;
            this.customerService = customerService;
        }
    @GetMapping("/alltrip")
    public String AllTrip(Model model, @Param("keyword") String keyword){
        List<TripDto> tripDtos = new ArrayList<>();
        if (keyword == null){
            tripDtos = tripService.getListTrip();
        }else {
            tripDtos = tripService.searchTrip(keyword);
            model.addAttribute("keyword", keyword);
        }
        model.addAttribute("trips", tripDtos);
        model.addAttribute("formatNumber", new FormatNumber());
        return "/pages/all-trip";
    }

    @GetMapping("/information/{tripId}/edit")
    public String editTrip(@PathVariable("tripId") long tripId, Model model){
            TripDto tripDto = tripService.getTripDetail(tripId);
            List<TourDto> tourDtos = tourService.findAllTours();
            model.addAttribute("trip", tripDto);
            model.addAttribute("tours", tourDtos);
            return "pages/edit-trip";
    }

    @PostMapping("/information/{tripId}/edit")
    public String saveEditTrip(@PathVariable("tripId") long tripId, @ModelAttribute("trip") TripDto tripDto, Model model){
            tripDto.setId(tripId);
            tripService.updateTrip(tripDto);
            return "redirect:/alltrip";
    }

    @GetMapping("/information/{id}")
    public String informationTrain(@PathVariable("id") long id, Model model){
        TripDto tripDto = tripService.getTripDetail(id);
        model.addAttribute("trip", tripDto);
        model.addAttribute("formatNumber", new FormatNumber());
        return "/pages/trip-information";
        }

    @GetMapping("/train-info/{id}")
    public String trainInfo(@PathVariable("id") long id, Model model){
        TripDto tripDto = tripService.getTripDetail(id);
        List<PassengerDto> customerDtos = tripService.getTripPassenger(tripDto);
        logger.info(String.valueOf(customerDtos.size()) + "===================================");

        model.addAttribute("trip", tripDto);
        model.addAttribute("formatNumber", new FormatNumber());
        model.addAttribute("customers", customerDtos);
            return "/pages/train-info";
        }

    @GetMapping("/information/{id}/delete")
    public String cancelTrip(@PathVariable("id") Long id){
            TripDto tripDto = tripService.getTripDetail(id);
            tripService.cancelTrip(tripDto);
            return "redirect:/alltrip";
    }
    @GetMapping("/add-new-trip")
    public String addTrip(Model model){
            List <TourDto> tourDtos = tourService.findAllTours();
            TripDto tripDto = new TripDto();
            model.addAttribute("trip", tripDto);
            model.addAttribute("tours", tourDtos);
            return "/pages/add-new-trip";
    }

    @PostMapping("/add-new-trip")
    public String saveTrip(@ModelAttribute("trip") TripDto tripDto){
        tripService.addNewTrip(tripDto);
        return "redirect:/alltrip";
    }
}

