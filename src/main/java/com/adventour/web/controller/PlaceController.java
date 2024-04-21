package com.adventour.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PlaceController {
//    private PlaceService placeService;

    //    @Autowired
//    public PlaceController(PlaceService placeService) {
//        this.placeService = placeService;
//    }
    @Autowired
    public PlaceController( ) {

    }
    @GetMapping("/all-place")
    public String allPlace(Model model){return "/pages/all-place";}

    @GetMapping("/place-detail")
    public String placeDetail(Model model){return "/pages/place-detail";}

    @GetMapping("/add-new-place")
    public String addPlace(Model model){return "/pages/add-new-place";}
}
