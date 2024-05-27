package com.adventour.web.controller;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.adventour.web.dto.AddScheduleFormData;
import com.adventour.web.dto.ScheduleDto;
import com.adventour.web.dto.TourDto;
import com.adventour.web.models.Schedule;
import com.adventour.web.models.Tour;
import com.adventour.web.service.TourService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TourController {
    private TourService tourService;
    static TourDto currentAddingTour;

    @Autowired
    public TourController(TourService tourService) {
        this.tourService = tourService;
    }
    private static Logger logger = LoggerFactory.getLogger(TourController.class);

    @GetMapping("/all-tour")
    public String allTour(Model model,  @Param("keyword") String keyword){
        List<TourDto> tourDtos = new ArrayList<>();
        if (keyword == null) {
            tourDtos =  tourService.findAllTours();
        } else {
            tourDtos = tourService.searchTour(keyword);
            model.addAttribute("keyword", keyword);
        }
        model.addAttribute("tours", tourDtos);
        return "/pages/all-tour";
    }

    @GetMapping("/all-tour/{tourId}")
    public String tourDetail(@PathVariable("tourId") long tourId, Model model){
        TourDto tourDto = tourService.findByTourId(tourId);
        model.addAttribute("tour", tourDto);
        return "/pages/tour-info";
    }

    @GetMapping("/all-tour/{tourId}/edit")
    public String editTour(@PathVariable("tourId") long tourId, Model model){
        TourDto tour = tourService.findByTourId(tourId);
        model.addAttribute("tour", tour);
        return "/pages/edit-tour";
    }

    @PostMapping("/all-tour/{tourId}/edit")
    public String updateTour(@PathVariable("tourId") long tourId, @ModelAttribute("tour") TourDto tour){
        tour.setId(tourId);
        tourService.editTourDetail(tour);
        return "redirect:/all-tour";
    }

    @GetMapping("/all-tour/{tourId}/delete")
    public String deleteTour(@PathVariable("tourId") long tourId){
        tourService.deleteTour(tourId);
        return "redirect:/all-tour";
    }

    @GetMapping("/tour-maps")
    public String tourMap(Model model){return "/pages/tour-map";}

    @GetMapping("/add-tour")
    public String addNewTour(Model model){
        TourDto tour = new TourDto();
        currentAddingTour = tour;
        model.addAttribute("tour", tour);
        return "/pages/add-tour";
    }

    @GetMapping("/save-tour")
    public String saveTour(@ModelAttribute("tour") TourDto tour, Model model){
        tourService.saveTour(tour);
        return "redirect:/all-tour";
    }

    @PostMapping("/add-tour")
    public String performingTour(@ModelAttribute("tour") TourDto tour, Model model){
        List<Integer> days = new ArrayList<>();
        AddScheduleFormData data = new AddScheduleFormData();
        for(int i =0; i < tour.getNumberOfDays(); i++){
            days.add(i+1);
        }

        data.schedule = new ScheduleDto();
        tour.schedules = new ArrayList<>();
        data.tour = tour;
        currentAddingTour = tour;
        model.addAttribute("days", days);
        model.addAttribute("data", data);

        logger.info(String.valueOf(tour.getNumberOfDays()));
        return "/pages/add-schedule";
    }

    @PostMapping("/add-schedule/{day}")
    public String addSchedule(@ModelAttribute("data") AddScheduleFormData data,
                              @PathVariable("day") int day,
                              Model model){
        List<Integer> days = new ArrayList<>();
        data.setTour(currentAddingTour);
        logger.info(String.valueOf("getNumberOfDays " + data.tour.getNumberOfDays()));
        logger.info(String.valueOf("getTourName " + data.tour.getTourName()));

        for(int i =0; i < data.tour.getNumberOfDays(); i++){
            days.add(i+1);
            if (i+1 == day) {
                ScheduleDto scheduleDto = new ScheduleDto();
                scheduleDto.setDayOfSchedule(day);
                currentAddingTour.schedules.add(scheduleDto);
            }
        }
        data.setTour(currentAddingTour);

        model.addAttribute("days", days);
        model.addAttribute("data", data);

        return "/pages/add-schedule";
    }
}
