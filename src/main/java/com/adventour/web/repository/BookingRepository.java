package com.adventour.web.repository;

import com.adventour.web.models.Booking;
import com.adventour.web.models.Customer;
import com.adventour.web.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
     Optional<Booking> findById(Long id);

    List<Booking> findByBookingDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Booking> findByCustomer(Customer customer);
    List<Booking> findByTrip(Trip trip);
//    @Query("SELECT b.id, b.bookingDate, b.status, b.totalAmount, b.numberAdult, b.numberChildren, b.numberBaby,  b.customer " +
//            "FROM Booking b ")
//    List<Booking> findBoookingAndCus();
    @Query("SELECT b.id, b.bookingDate, b.status, b.totalAmount, b.numberAdult, b.numberChildren, b.numberBaby,  b.customer " +
            "FROM Booking b "
            )
    List<Object[]> findBookingsWithCustomer();
    @Query("SELECT b.id, b.bookingDate, b.status, b.totalAmount, b.numberAdult, b.numberChildren, b.numberBaby,  b.customer " +
            "FROM Booking b "
    )
    List<Object[]> findBookingSimple();

    @Query("SELECT t.id, t.tour.tourName, (b.numberAdult + b.numberChildren + b.numberBaby), t.tour.typeOfTour, b.status " +
            "FROM Booking b " +
            "JOIN b.trip t " +
            "WHERE b.bookingDate >= :startDay AND b.bookingDate <= :endDay"
            )
    List<Object[]> findRecentBookingsWithTripInfoAndTicketCount(@Param("startDay")LocalDateTime startDay,@Param("endDay") LocalDateTime endDay);

    @Query("SELECT DATE(b.bookingDate) as date, COUNT(b.id) as count " +
            "FROM Booking b " +
            "GROUP BY DATE(b.bookingDate) " +
            "ORDER BY DATE(b.bookingDate)")
    List<Object[]> findBookingStatistics();

}
