package com.adventour.web.controller;

import com.adventour.web.dto.BookingDashboardDto;
import com.adventour.web.dto.PaymentDashboardDto;
import com.adventour.web.dto.TripDto;
import com.adventour.web.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

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
        List<BookingDashboardDto> bookingDashboardDtos = dashboardService.getBookingAndTourNameIn7Days();
        List<PaymentDashboardDto> paymentDashboardDtos = dashboardService.getRecentPayment();
        List<TripDto> tripDtos = dashboardService.getListTripInMonth();
        List<Integer> tripDays = tripDtos.stream()
                .map(tripDto -> tripDto.getStartDate().getDayOfMonth())
                .toList();
        model.addAttribute("tripDays",tripDays);
        model.addAttribute("totalCustomers", totalCustomers);
        model.addAttribute("totalTickets", totalTickets);
        model.addAttribute("todayTrips", todayTrips);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("dashboardBookings", bookingDashboardDtos);
        model.addAttribute("recentPayings", paymentDashboardDtos);
        return "/pages/dashboard";
    }
}
