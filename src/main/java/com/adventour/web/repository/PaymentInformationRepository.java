package com.adventour.web.repository;

import com.adventour.web.models.Booking;
import com.adventour.web.models.Passenger;
import com.adventour.web.models.PaymentInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PaymentInformationRepository extends JpaRepository<PaymentInformation, Long> {
    Optional<PaymentInformation> findById(Long id);

    Set<PaymentInformation> findByBooking(Booking booking);

    @Query("SELECT p, c.nameCustomer " +
            "FROM PaymentInformation p JOIN p.booking b JOIN b.customer c " +
            "WHERE p.paymentTime >= :startDay AND p.paymentTime <= :endDay")
    List<Object[]> getRecentPaymentWithCustomerName(@Param("startDay") LocalDateTime startDay, @Param("endDay") LocalDateTime endDay);

    @Query("SELECT SUM(p.amountOfMoney) " +
            "FROM PaymentInformation p " +
            "WHERE p.paymentTime >= :startDay AND p.paymentTime <= :endDay")
    long getRevenueInSomeDays(@Param("startDay") LocalDateTime startDay, @Param("endDay") LocalDateTime endDay);



}