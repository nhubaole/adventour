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
    public Long id;

    @Min(value = 0)
    public int slots;

    public LocalDateTime startDate;

    public LocalDateTime endDate;

    @Min(value = 0)
    public int minPassenger;

    @Min(value = 0)
    public int actualPassenger;

    private Tour tour;
    private TypeOfTrip typeOfTrip;
    private Set<Schedule> schedules ;
}
