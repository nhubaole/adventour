package com.adventour.web.controller;

import com.adventour.web.service.BucketService;
import com.adventour.web.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
    public String allPlace(Model model){return "/pages/all-place";}

    @GetMapping("/place-detail")
    public String placeDetail(Model model){return "/pages/place-detail";}

    @GetMapping("/add-new-place")
    public String addPlace(Model model){return "/pages/add-new-place";}

    @PostMapping("/add-new-place")
    public String uploadFile1(Model model, @RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            String fileUrl = bucketService.uploadFile(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            model.addAttribute("message", message);
            model.addAttribute("fileUrl", fileUrl);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            model.addAttribute("message", message);
        }

        return "/pages/add-new-place";
    }
}
