package com.adventour.web.controller;

import com.adventour.web.dto.LocationDto;
import com.adventour.web.dto.TourDto;
import com.adventour.web.models.Schedule;
import com.adventour.web.models.Tour;
import com.adventour.web.service.BucketService;
import com.adventour.web.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PlaceController {
    private LocationService placeService;
    private BucketService bucketService;

        @Autowired
    public PlaceController(LocationService placeService, BucketService bucketService) {
        this.placeService = placeService;
        this.bucketService = bucketService;
    }
    @GetMapping("/all-place")
    public String allPlace(Model model,  @Param("keyword") String keyword){
        List<LocationDto> locationDtos = new ArrayList<>();
        if (keyword == null) {
            locationDtos =  placeService.findAllLocation();
        } else {
            locationDtos = placeService.searchLocation(keyword);
            model.addAttribute("keyword", keyword);
        }
        model.addAttribute("places", locationDtos);
        return "/pages/all-place";
    }

    @GetMapping("/all-place/{placeId}/edit")
    public String editPlace(@PathVariable("placeId") long placeId, Model model){
        LocationDto place = placeService.getLocationById(placeId);
        model.addAttribute("place", place);
        return "/pages/edit-place";
    }

    @PostMapping("/all-place/{placeId}/edit")
    public String updateTour(@PathVariable("placeId") long placeId, @ModelAttribute("place") LocationDto place){
        place.setId(placeId);
        placeService.editLocation(place);
        return "redirect:/all-place";
    }

    @GetMapping("/all-place/{placeId}/delete")
    public String deleteTour(@PathVariable("placeId") long placeId){
        placeService.deleteLocation(placeId);
        return "redirect:/all-place";
    }

    @GetMapping("/all-place/{placeId}")
    public String placeDetail(@PathVariable("placeId") long placeId, Model model){
        LocationDto locationDto = placeService.getLocationById(placeId);
        model.addAttribute("place", locationDto);
        return "/pages/place-detail";
    }

    @GetMapping("/add-new-place")
    public String addPlace(Model model){
        LocationDto place = new LocationDto();
        place.setImages(new String[]{});
        model.addAttribute("place", place);
        return "/pages/add-new-place";
        }

    @PostMapping("/add-new-place")
    public String savePlace(@ModelAttribute("place") LocationDto place){
        placeService.addNewLocation(place);
        return "redirect:/all-place";
    }

//    @PostMapping("/add-new-place")
//    public String uploadFile1(Model model, @RequestParam("file") MultipartFile file) {
//        String message = "";
//        try {
//            String fileUrl = bucketService.uploadFile(file);
//            message = "Uploaded the file successfully: " + file.getOriginalFilename();
//            model.addAttribute("message", message);
//            model.addAttribute("fileUrl", fileUrl);
//        } catch (Exception e) {
//            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
//            model.addAttribute("message", message);
//        }
//
//        return "/pages/add-new-place";
//    }
}
