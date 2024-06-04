package com.adventour.web.dto;

import com.adventour.web.models.*;
import jakarta.validation.constraints.Min;
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

    private String code;

    private String name;

    @Min(value = 0)
    private int slots;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Min(value = 0)
    private int actualPassenger = 0;

    private int priceTicket;

    private double discount;

    private int actualPrice;

    private TourDto tourDto;
    private String tripType;
    private String status;
//    private Set<TourGuide> tourGuides;
    private Set<Passenger> passengers;

    //private Set<Schedule> schedules ;

}
