package com.adventour.web.service;


import com.adventour.web.dto.BookingStatisticData;
import com.adventour.web.dto.RevenueByTourTypeData;

import java.util.List;

public interface StatisticService {

    List<Integer> getRevenuePerMonth();
    RevenueByTourTypeData getRevenueThisWeek();
    BookingStatisticData getBookingStatistic();
    double getSuccessfulTripRate();
}
