package com.adventour.web.service.impl;

import com.adventour.web.dto.PaymentInformationDto;
import com.adventour.web.dto.TripDto;
import com.adventour.web.mapper.Mapper;
import com.adventour.web.models.PaymentInformation;
import com.adventour.web.models.Trip;
import com.adventour.web.repository.*;
import com.adventour.web.service.DashboardService;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {
    private final CustomerRepository customerRepository;
    private final TripRepository tripRepository;

    private final TicketRepository ticketRepository;
    private final PaymentInformationRepository paymentInformationRepository;

    private final BookingRepository bookingRepository;
    private final Mapper mapper;

    public DashboardServiceImpl(CustomerRepository customerRepository, TripRepository tripRepository, TicketRepository ticketRepository, PaymentInformationRepository paymentInformationRepository, BookingRepository bookingRepository, Mapper mapper) {
        this.customerRepository = customerRepository;
        this.tripRepository = tripRepository;
        this.ticketRepository = ticketRepository;
        this.paymentInformationRepository = paymentInformationRepository;
        this.bookingRepository = bookingRepository;
        this.mapper = mapper;
    }

    @Override
    public long getTotalCustomer() {
        return customerRepository.count();
    }

    @Override
    public long getTotalTicketCount() {
        return ticketRepository.count();
    }

    @Override
    public long getTodayTripCount() {
        return  tripRepository.countTripsStartingToday();
    }

    @Override
    public List<TripDto> getListTripInMonth() {
        List<TripDto> rs = new ArrayList<>();
        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate lastDayOfMonth = firstDayOfMonth.plusMonths(1).minusDays(1);

        LocalDateTime startOfMonth = firstDayOfMonth.atStartOfDay();
        LocalDateTime endOfMonth = lastDayOfMonth.atTime(LocalTime.MAX);

        List<Trip> trips = tripRepository.findTripsForCurrentMonth(startOfMonth, endOfMonth);
        for(Trip trip: trips){
            rs.add(mapper.mapToTripDto(trip));
        }

        return rs;
    }

    @Override
    public List<Object[]> getRecentPayment() {
        //lay trong 7 ngay gan day
        LocalDateTime endDay = LocalDateTime.now();
        LocalDateTime startDay = endDay.minusDays(7);
        return paymentInformationRepository.getRecentPaymentWithCustomerName(startDay, endDay);

    }

    @Override
    public long getRevenueIn30Days() {
        LocalDateTime endDay = LocalDateTime.now();
        LocalDateTime startDay = endDay.minusDays(30);
        return paymentInformationRepository.getRevenueInSomeDays(startDay, endDay);
    }

    @Override
    public List<Object[]> getBookingAndTourNameIn7Days() {
        LocalDateTime startDay = LocalDateTime.now();
        LocalDateTime endDay = startDay.minusDays(7);
        return bookingRepository.findRecentBookingsWithTripInfoAndTicketCount(endDay, startDay);
    }
}
