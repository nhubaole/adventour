package com.adventour.web.dto;

import com.adventour.web.enums.StatusOfBooking;
import com.adventour.web.models.*;
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

    private String code;

    private LocalDateTime bookingDate;

    @NotNull
    private StatusOfBooking status = StatusOfBooking.PENDING;

    @NotNull
    @Min(value = 0)
    private int numberOfPassengers;

    @Min(value = 0)
    private int totalAmount;

    private int amountPaid = 0;

    private String amountPaidFormat;

    @Min(value = 0)
    private int numberAdult;

    @Min(value = 0)
    private int numberChildren;

    @Min(value = 0)
    private int numberBaby;

    @NotNull
    private TripDto tripDto;

    @NotNull
    private CustomerDto customerDto;

    private Set<PassengerDto> passengerDtos = new HashSet<>();
    private Set<PaymentInformationDto> paymentInformationDtos = new HashSet<>();
    private Set<TicketDto> ticketDtos = new HashSet<>();
}
