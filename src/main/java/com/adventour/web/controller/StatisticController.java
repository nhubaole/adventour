package com.adventour.web.controller;

import com.adventour.web.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class StatisticController {
    private StatisticService statisticService;
    @Autowired
    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }
    @GetMapping("/statistic")
    public String statistic(Model model){
        List<Integer> revenuePerMonth = statisticService.getRevenuePerMonth();

        model.addAttribute("revenuePerMonth", revenuePerMonth);
        return "/pages/statistic";
    }
}
