package com.adventour.web.controller;

import com.adventour.web.dto.BookingStatisticData;
import com.adventour.web.dto.RevenueByTourTypeData;
import com.adventour.web.service.StatisticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StatisticController {
    private StatisticService statisticService;
    private static Logger logger = LoggerFactory.getLogger(StatisticController.class);

    @Autowired
    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }
    @GetMapping("/statistic")
    public String statistic(Model model){
        List<Integer> revenuePerMonth = statisticService.getRevenuePerMonth();
        RevenueByTourTypeData revenueByTourTypeData = statisticService.getRevenueThisWeek();
        BookingStatisticData bookingStatisticData = statisticService.getBookingStatistic();
        List<Double> successfulTripRate = new ArrayList<>(){};
        successfulTripRate.add(BigDecimal.valueOf(statisticService.getSuccessfulTripRate())
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue());
        logger.info(String.valueOf(successfulTripRate));

        model.addAttribute("revenuePerMonth", revenuePerMonth);
        model.addAttribute("revenueByTourTypeData", revenueByTourTypeData);
        model.addAttribute("bookingStatisticData", bookingStatisticData);
        model.addAttribute("successfulTripRate", successfulTripRate);
        return "/pages/statistic";
    }
}
