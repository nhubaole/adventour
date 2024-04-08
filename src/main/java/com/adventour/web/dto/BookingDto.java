package com.adventour.web.dto;

import com.adventour.web.enums.StatusOfBooking;
import com.adventour.web.models.Customer;
import com.adventour.web.models.Passenger;
import com.adventour.web.models.PaymentInformation;
import com.adventour.web.models.Ticket;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    @NotNull
    private Long id;

    private LocalDateTime bookingDate;
    private String [] imagesUrl;

    @NotNull
    private StatusOfBooking status;

    @NotNull
    @Min(value = 0)
    private int numberOfPassengers;

    @Min(value = 0)
    private int totalAmount;

    private int amountPaid;

    private Long trip;
    private Customer customer;
    private Set<Passenger> passengers;
    private Set<PaymentInformation> paymentInformation = new HashSet<>();
    private Set<Ticket> tickets;


}
