package com.adventour.web.dto;

import com.adventour.web.models.*;
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
public class TripDto {
    private Long id;


    @Min(value = 0)
    private int slots;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Min(value = 0)
    private int minPassenger;

    @Min(value = 0)
    private int actualPassenger;

    private int priceTicket;

    private double discount;

    private Tour tour;
    private String typeOfTrip;
//    private Set<TourGuide> tourGuides;
    private Set<Passenger> passengers;

    //private Set<Schedule> schedules ;

}
