package com.adventour.web.dto;

import com.adventour.web.models.Schedule;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourDto {
    private Long id;

    @NotEmpty(message = "Name of tour can't be empty")
    private String tourName;

    private String departureLocation;

    @Min(value = 0)
    private int estimatedPrice;

    @Min(value = 0)
    private int numberOfDays;

    @Min(value = 0)
    private int numberOfNights;

    @NotNull
    private String typeOfTour;

    private Set<Schedule> schedules;

}
