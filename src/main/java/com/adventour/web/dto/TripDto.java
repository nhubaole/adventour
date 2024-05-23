package com.adventour.web.dto;

import com.adventour.web.models.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripDto {
    @DateTimeFormat(pattern = "dd/MM/yyyy")

    private Long id;

    private String name;

    @Min(value = 0)
    private int slots;

    private String tripID;
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Min(value = 0)
    private int actualPassenger = 0;

    private int priceTicket;

    private double discount;


    private String tripType;
//    private Set<TourGuide> tourGuides;
    private Set<Passenger> passengers;

    //private Set<Schedule> schedules ;
    @NotNull
    private TicketDto ticketDto;

    @NotNull
    private TourDto tourDto;

}
