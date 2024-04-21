package com.adventour.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class StatisticController {
    @Autowired
    public StatisticController( ) {

    }
    @GetMapping("/statistic")
    public String statistic(Model model){return "/pages/statistic";}
}
