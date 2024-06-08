package com.adventour.web.service.impl;

import com.adventour.web.controller.TourController;
import com.adventour.web.dto.BookingDashboardDto;
import com.adventour.web.dto.PaymentDashboardDto;
import com.adventour.web.dto.PaymentInformationDto;
import com.adventour.web.dto.TripDto;
import com.adventour.web.enums.StatusOfBooking;
import com.adventour.web.mapper.Mapper;
import com.adventour.web.models.PaymentInformation;
import com.adventour.web.models.Schedule;
import com.adventour.web.models.Tour;
import com.adventour.web.models.Trip;
import com.adventour.web.repository.*;
import com.adventour.web.service.DashboardService;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class DashboardServiceImpl implements DashboardService {
    private final CustomerRepository customerRepository;
    private final TripRepository tripRepository;

    private final TourRepository tourRepository;

    private final TicketRepository ticketRepository;
    private final PaymentInformationRepository paymentInformationRepository;

    private final BookingRepository bookingRepository;
    private final Mapper mapper;

    public DashboardServiceImpl(CustomerRepository customerRepository, TripRepository tripRepository, TourRepository tourRepository, TicketRepository ticketRepository, PaymentInformationRepository paymentInformationRepository, BookingRepository bookingRepository, Mapper mapper) {
        this.customerRepository = customerRepository;
        this.tripRepository = tripRepository;
        this.tourRepository = tourRepository;
        this.ticketRepository = ticketRepository;
        this.paymentInformationRepository = paymentInformationRepository;
        this.bookingRepository = bookingRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public long getTotalCustomer() {
        return customerRepository.count();
    }

    @Override
    @Transactional
    public long getTotalTicketCount() {
        return ticketRepository.count();
    }

    @Override
    @Transactional
    public long getTodayTripCount() {
        return  tripRepository.countTripsStartingToday();
    }

    @Override
    @Transactional
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
    @Transactional
    public List<PaymentDashboardDto> getRecentPayment() {
        //lay trong 7 ngay gan day
        List<PaymentDashboardDto> rs = new ArrayList<>();
        LocalDateTime endDay = LocalDateTime.now();
        LocalDateTime startDay = endDay.minusDays(7);
        List<Object[]> payments = paymentInformationRepository.getRecentPaymentWithCustomerName(startDay, endDay);

        for(Object[] row : payments){
            PaymentInformation paymentInformation = (PaymentInformation) row[0];
            String nameCus = (String) row[1];

            int amountPaid = paymentInformation.getAmountOfMoney();
            LocalDateTime dateTime = paymentInformation.getPaymentTime();
            PaymentDashboardDto paymentDashboardDto = new PaymentDashboardDto(nameCus, String.format("%,d",amountPaid ), dateTime);
            rs.add(paymentDashboardDto);

        }

        return rs;


    }

    @Override
    @Transactional
    public long getRevenueIn30Days() {
        LocalDateTime endDay = LocalDateTime.now();
        LocalDateTime startDay = endDay.minusDays(30);
        Long revenue = paymentInformationRepository.getRevenueInSomeDays(startDay, endDay);
        return revenue == null ? 0 : revenue;
    }

    @Override
    @Transactional
    public List<BookingDashboardDto> getBookingAndTourNameIn7Days() {
        List<BookingDashboardDto> rs = new ArrayList<>();
        LocalDateTime startDay = LocalDateTime.now();
        LocalDateTime endDay = startDay.minusDays(7);
        List<Object[]> objects = bookingRepository.findRecentBookingsWithTripInfoAndTicketCount(endDay, startDay);
        for (Object[] row : objects){
            long idTrip = (long) row[0];
            String nameTour = (String) row[1];
            int numberTickets = (int) row[2];
            String typeTour = (String) row[3];
            StatusOfBooking statusBooking= (StatusOfBooking) row[4];
            String status = statusBooking.name();

            String image = getImageForBooking (idTrip);
            BookingDashboardDto booking = new BookingDashboardDto();
            booking.setTourName(nameTour);
            booking.setNumberPass(numberTickets);
            booking.setTypeOfTour(typeTour);
            booking.setStatusOfBooking(status);
            booking.setImageUrl(image);

            rs.add(booking);
        }
        return rs;
    }
    private static Logger logger = LoggerFactory.getLogger(DashboardServiceImpl.class);

    private String getImageForBooking(long idTrip) {

        Trip trip = tripRepository.findById(idTrip).orElse(null);
        if(trip != null){
            List<Schedule> schedules = trip.getTour().getSchedules().stream().toList();

            if(!schedules.isEmpty() && schedules.get(0) != null){
                String[] images = schedules.get(0).getStartLocation().getImages();
                if(images != null && images.length > 0){
                    return images[0];
                }
            }
        }
        return "testBucket/images__2_.jpg";


    }
}
