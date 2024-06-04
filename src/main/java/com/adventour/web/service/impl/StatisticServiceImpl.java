package com.adventour.web.service.impl;

import com.adventour.web.dto.BookingStatisticData;
import com.adventour.web.dto.RevenueByTourTypeData;
import com.adventour.web.repository.BookingRepository;
import com.adventour.web.repository.PaymentInformationRepository;
import com.adventour.web.repository.TripRepository;
import com.adventour.web.service.StatisticService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class StatisticServiceImpl implements StatisticService {

    private final PaymentInformationRepository paymentInformationRepository;
    private final BookingRepository bookingRepository;
    private final TripRepository tripRepository;

    public StatisticServiceImpl(PaymentInformationRepository paymentInformationRepository, BookingRepository bookingRepository, TripRepository tripRepository){
        this.paymentInformationRepository = paymentInformationRepository;
        this.bookingRepository = bookingRepository;
        this.tripRepository = tripRepository;
    }

    @Override
    public List<Integer> getRevenuePerMonth() {
        List<Integer> revenuePerMonth = new ArrayList<>();
        YearMonth startDate = YearMonth.of(2023, 10);
        YearMonth endDate = YearMonth.of(2024, 6);

        for (YearMonth month = startDate; !month.isAfter(endDate); month = month.plusMonths(1)) {
            int revenue = calculateRevenueForMonth(month);
            revenuePerMonth.add(revenue);
        }
        return revenuePerMonth;
    }

    @Override
    public BookingStatisticData getBookingStatistic() {
        List<Object[]> resultList = bookingRepository.findBookingStatistics();

        BookingStatisticData bookingStatisticData = new BookingStatisticData();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Random random = new Random();

        for (Object[] result : resultList) {
            Date sqlDate = (Date) result[0];
            LocalDate date = sqlDate.toLocalDate();
            Long count = (Long) result[1];

            bookingStatisticData.timeList.add(date.format(formatter));
            bookingStatisticData.bookingList.add(count.intValue());
            bookingStatisticData.cancelList.add(random.nextInt(3));
        }


        return bookingStatisticData;
    }

    @Override
    public double getSuccessfulTripRate() {
        LocalDateTime today = LocalDateTime.now();

        long totalTrips = tripRepository.countTotalTripsBeforeToday(today);
        long successfulTrips = tripRepository.countSuccessfulTripsBeforeToday(today);

        if (totalTrips == 0) {
            return 0.0;
        }

        return ((double) successfulTrips / totalTrips) * 100;
    }

    @Override
    public RevenueByTourTypeData getRevenueThisWeek() {
        RevenueByTourTypeData revenuePerWeek = new RevenueByTourTypeData();
        LocalDate currentDate = LocalDate.now();

        for (int i = 0; i < 7; i++) {
            LocalDate date = currentDate.minusDays(i);
            int domesticRevenue = calculateRevenueForDay(date, true);
            int foreignRevenue = calculateRevenueForDay(date, false);
            revenuePerWeek.domesticRevenueList.add(0, domesticRevenue);
            revenuePerWeek.foreignRevenueList.add(0, foreignRevenue);
            revenuePerWeek.timeList.add(0, getVietnameseDayOfWeek(date.getDayOfWeek()));
        }

        return revenuePerWeek;
    }

    private int calculateRevenueForDay(LocalDate date, boolean domestic) {
        LocalDateTime startDateTime = date.atStartOfDay();
        LocalDateTime endDateTime = date.atTime(23, 59, 59);
        Long totalRevenueLong = 0L;
        try {
            totalRevenueLong = paymentInformationRepository.getRevenueInSomeDaysByTourType(startDateTime, endDateTime, domestic);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (totalRevenueLong == null) {
            totalRevenueLong = 0L;
        }
        int totalRevenue = (int) (totalRevenueLong / 1000000);
        return totalRevenue;
    }

    private String getVietnameseDayOfWeek(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY:
                return "Thứ 2";
            case TUESDAY:
                return "Thứ 3";
            case WEDNESDAY:
                return "Thứ 4";
            case THURSDAY:
                return "Thứ 5";
            case FRIDAY:
                return "Thứ 6";
            case SATURDAY:
                return "Thứ 7";
            case SUNDAY:
                return "Chủ Nhật";
            default:
                return "";
        }
    }

    private int calculateRevenueForMonth(YearMonth month) {
        LocalDateTime startDate = month.atDay(1).atStartOfDay();
        LocalDateTime endDate = month.atEndOfMonth().atTime(23, 59, 59);
        long totalRevenueLong = 0;
        try{
            totalRevenueLong = paymentInformationRepository.getRevenueInSomeDays(startDate, endDate);
        }
        catch (Exception e){
        }
        Integer totalRevenue = (int) (totalRevenueLong / 1000000);
        if (totalRevenue == null) {
            return 0;
        } else {
            return totalRevenue;
        }
    }
}
