package com.adventour.web.controller;

import com.adventour.web.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    private DashboardService dashboardService;

    @Autowired
    public DashboardController(DashboardService dashboardService){
        this.dashboardService = dashboardService;
    }
    @GetMapping("/dashboard")
    public String getDashboard(Model model){
        long totalCustomers = dashboardService.getTotalCustomer();
        long totalTickets = dashboardService.getTotalTicketCount();
        long todayTrips = dashboardService.getTodayTripCount();
        long totalRevenue =  dashboardService.getRevenueIn30Days();

        model.addAttribute("totalCustomers", totalCustomers);
        model.addAttribute("totalTickets", totalTickets);
        model.addAttribute("todayTrips", todayTrips);
        model.addAttribute("totalRevenue", totalRevenue);
        return "/pages/dashboard";
    }
}
