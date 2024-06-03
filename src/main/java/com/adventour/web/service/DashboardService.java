package com.adventour.web.service;

import com.adventour.web.dto.BookingDashboardDto;
import com.adventour.web.dto.PaymentDashboardDto;
import com.adventour.web.dto.PaymentInformationDto;
import com.adventour.web.dto.TripDto;

import java.util.List;

public interface DashboardService {

    long getTotalCustomer();
    long getTotalTicketCount();
    long getTodayTripCount();
    List<TripDto> getListTripInMonth();

    List<PaymentDashboardDto> getRecentPayment();

    long getRevenueIn30Days();

    List<BookingDashboardDto> getBookingAndTourNameIn7Days();


}
