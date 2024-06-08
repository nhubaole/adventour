package com.adventour.web.dto;

import com.adventour.web.models.*;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Null;
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

    private String code;
    private Boolean isActive;

    private String name;

    @Min(value = 0)
    @Nullable
    private Integer slots;

    @Nullable
    private LocalDateTime startDate;

    @Nullable
    private LocalDateTime endDate;

    @Min(value = 0)
    private int actualPassenger = 0;

    private int priceTicket;

    @Nullable
    private Double discount;

    private int actualPrice;

    private TourDto tourDto;
    private String tripType;
    private String status;
//    private Set<TourGuide> tourGuides;
    private Set<Passenger> passengers;

    //private Set<Schedule> schedules ;

}
