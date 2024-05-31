package com.adventour.web.service;

import com.adventour.web.dto.PaymentInformationDto;
import com.adventour.web.dto.TripDto;

import java.util.List;

public interface DashboardService {

    long getTotalCustomer();
    long getTotalTicketCount();
    long getTodayTripCount();
    List<TripDto> getListTripInMonth();

    List<Object[]> getRecentPayment();

    long getRevenueIn30Days();

    List<Object[]> getBookingAndTourNameIn7Days();


}
