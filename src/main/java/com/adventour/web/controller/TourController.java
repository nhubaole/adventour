package com.adventour.web.controller;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.adventour.web.dto.AddScheduleFormData;
import com.adventour.web.dto.LocationDto;
import com.adventour.web.dto.ScheduleDto;
import com.adventour.web.dto.TourDto;
import com.adventour.web.models.Schedule;
import com.adventour.web.models.Tour;
import com.adventour.web.service.LocationService;
import com.adventour.web.service.TourService;
import com.adventour.web.utils.FormatNumber;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TourController {
    private TourService tourService;
    private LocationService locationService;

    @Autowired
    public TourController(TourService tourService, LocationService locationService) {
        this.tourService = tourService;
        this.locationService = locationService;
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
//        logger.info(tourDtos.get(0).getTypeOfTour());
        model.addAttribute("tours", tourDtos);
        return "/pages/all-tour";
    }

    @GetMapping("/all-tour/{tourId}")
    public String tourDetail(@PathVariable("tourId") long tourId, Model model){
        TourDto tourDto = tourService.findByTourId(tourId);
        model.addAttribute("tour", tourDto);
        model.addAttribute("formatNumber", new FormatNumber());
        return "/pages/tour-info";
    }

    @GetMapping("/all-tour/{tourId}/edit")
    public String editTour(@PathVariable("tourId") long tourId, Model model){
        TourDto tour = tourService.findByTourId(tourId);
        model.addAttribute("tour", tour);
        return "/pages/edit-tour";
    }

//    @PostMapping("/all-tour/{tourId}/edit/save")
//    public String editTour(@PathVariable("tourId") long tourId, @ModelAttribute("tour") TourDto tour){
//        tour.setId(tourId);
//        tourService.editTourDetail(tour);
//        return "redirect:/all-tour";
//    }

    @PostMapping("/all-tour/{tourId}/edit")
    public String updateTour(@PathVariable("tourId") long tourId, @ModelAttribute("tour") TourDto tour, Model model, HttpSession session){
        List<Integer> days = new ArrayList<>();
        TourDto tourDto = tourService.findByTourId(tourId);

        tour.setId(tourId);
        tour.setNumberOfNights((tour.getNumberOfDays() == null ? 0: tour.getNumberOfDays()) - 1);
        tour.setSchedules(tourDto.getSchedules());
        AddScheduleFormData data = new AddScheduleFormData();
        for(int i = 0; i < tour.getNumberOfDays(); i++){
            days.add(i + 1);
        }

        data.schedule = new ScheduleDto();
        data.tour = tour;
        session.setAttribute("currentAddingTour", tour);

        model.addAttribute("days", days);
        model.addAttribute("data", data);

        List<LocationDto> locations = locationService.findAllLocation();
        model.addAttribute("locations", locations);
        return "/pages/edit-schedule";
    }

    @GetMapping("/all-tour/{tourId}/delete")
    public String deleteTour(@PathVariable("tourId") long tourId){
        tourService.deleteTour(tourId);
        return "redirect:/all-tour";
    }

    @GetMapping("/tour-itinerary/{tourId}")
    public String tourMap(@PathVariable("tourId") long tourId, Model model){
        TourDto tourDto = tourService.findByTourId(tourId);
        List<Integer> days = new ArrayList<>();
        for(int i = 0; i < tourDto.getNumberOfDays(); i++){
            days.add(i + 1);
        }
        model.addAttribute("days", days);
        model.addAttribute("tour", tourDto);
        return "/pages/tour-map";
    }

    @GetMapping("/add-tour")
    public String addNewTour(Model model, HttpSession session){
        TourDto tour = new TourDto();
        session.setAttribute("currentAddingTour", tour);

        model.addAttribute("tour", tour);
        return "/pages/add-tour";
    }

    @PostMapping("/save-tour")
    public String saveTour(HttpSession session){
        TourDto currentAddingTour = (TourDto) session.getAttribute("currentAddingTour");
        if (currentAddingTour == null) {
            return "redirect:/add-tour";
        }

        logger.info(String.valueOf("currentAddingTour " + currentAddingTour.toString()));
        tourService.saveTour(currentAddingTour);
        session.removeAttribute("currentAddingTour");
        return "redirect:/all-tour";
    }

    @PostMapping("/add-tour")
    public String performingTour(@ModelAttribute("tour") TourDto tour, Model model, HttpSession session){
        List<Integer> days = new ArrayList<>();
        AddScheduleFormData data = new AddScheduleFormData();
        for(int i = 0; i < tour.getNumberOfDays(); i++){
            days.add(i + 1);
        }

        data.schedule = new ScheduleDto();
        tour.schedules = new ArrayList<>();
        tour.setNumberOfNights(tour.getNumberOfDays() - 1);
        data.tour = tour;
        session.setAttribute("currentAddingTour", tour);

        model.addAttribute("days", days);
        model.addAttribute("data", data);

        List<LocationDto> locations = locationService.findAllLocation();
        model.addAttribute("locations", locations);

        return "/pages/add-schedule";
    }

    @PostMapping("/add-schedule/{day}")
    public String addSchedule(@ModelAttribute("data") AddScheduleFormData data,
                              @PathVariable("day") int day,
                              Model model, HttpSession session){
        TourDto currentAddingTour = (TourDto) session.getAttribute("currentAddingTour");
        if (currentAddingTour == null) {
            // Handle error: session expired or invalid flow
            return "redirect:/add-tour";
        }

        List<Integer> days = new ArrayList<>();
        data.setTour(currentAddingTour);

        List<String> vehicles = data.schedule.getVehicles().isEmpty() || data.schedule.getVehicles().get(0).trim().isEmpty()
                ? null
                : Arrays.stream(data.schedule.getVehicles().get(0).split("\n"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .collect(Collectors.toList());

        List<String> restaurants = data.schedule.getRestaurants().isEmpty() || data.schedule.getRestaurants().get(0).trim().isEmpty()
                ? null
                : Arrays.stream(data.schedule.getRestaurants().get(0).split("\n"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .collect(Collectors.toList());

        List<String> hotels = data.schedule.getHotels().isEmpty() || data.schedule.getHotels().get(0).trim().isEmpty()
                ? null
                : Arrays.stream(data.schedule.getHotels().get(0).split("\n"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .collect(Collectors.toList());

        List<String> otherServices = data.schedule.getOtherServices().isEmpty() || data.schedule.getOtherServices().get(0).trim().isEmpty()
                ? null
                : Arrays.stream(data.schedule.getOtherServices().get(0).split("\n"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .collect(Collectors.toList());


        for (int i = 0; i < (data.tour.getNumberOfDays() == null ? 0: data.tour.getNumberOfDays()) ; i++){
            days.add(i + 1);
            if (i + 1 == day) {
                ScheduleDto scheduleDto = new ScheduleDto();
                scheduleDto.setStartLocation(locationService.getLocationById(data.schedule.getStartLocation().getId()));
                scheduleDto.setEndLocation(locationService.getLocationById(data.schedule.getEndLocation().getId()));
                scheduleDto.setDayOfSchedule(day);
                scheduleDto.setTour(currentAddingTour);
                scheduleDto.setVehicles(vehicles);
                scheduleDto.setRestaurants(restaurants);
                scheduleDto.setHotels(hotels);
                scheduleDto.setOtherServices(otherServices);
                currentAddingTour.schedules.add(scheduleDto);
            }
        }
        data.setTour(currentAddingTour);
        session.setAttribute("currentAddingTour", currentAddingTour);

        data.schedule = new ScheduleDto();

        model.addAttribute("days", days);
        model.addAttribute("data", data);

        List<LocationDto> locations = locationService.findAllLocation();
        model.addAttribute("locations", locations);

        return "/pages/add-schedule";
    }

    @PostMapping("/deleteSchedule")
    public String deleteSchedule(@RequestParam("scheduleIndex") int scheduleIndex, Model model, HttpSession session) {
        TourDto currentAddingTour = (TourDto) session.getAttribute("currentAddingTour");
        if (currentAddingTour == null) {
            return "redirect:/add-tour";
        }

        if (currentAddingTour.getSchedules() != null && scheduleIndex >= 0 && scheduleIndex < currentAddingTour.getSchedules().size()) {
            currentAddingTour.getSchedules().remove(scheduleIndex);
        }

        List<Integer> days = new ArrayList<>();
        AddScheduleFormData data = new AddScheduleFormData();
        for(int i = 0; i < currentAddingTour.getNumberOfDays(); i++){
            days.add(i + 1);
        }

        data.schedule = new ScheduleDto();
        data.tour = currentAddingTour;

        model.addAttribute("days", days);
        model.addAttribute("data", data);

        List<LocationDto> locations = locationService.findAllLocation();
        model.addAttribute("locations", locations);

        return "/pages/add-schedule";
    }
}
