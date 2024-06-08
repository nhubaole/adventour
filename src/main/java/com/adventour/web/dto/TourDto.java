package com.adventour.web.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class TourDto {
    private Long id;

    private String tourName;

    @Nullable
    private String departureLocation;

    @Min(value = 0)
    @Nullable
    private Integer estimatedPrice;

    @Nullable
    @Min(value = 0)
    private Integer numberOfDays;

    @Min(value = 0)
    private int numberOfNights;

    @Nullable
    private String typeOfTour;

    public List<ScheduleDto> schedules;
    public String code;

    @Override
    public String toString() {
        return "TourDto{" +
                "id=" + id +
                ", tourName='" + tourName + '\'' +
                ", estimatedPrice=" + estimatedPrice +
                ", numberOfDays=" + numberOfDays +
                ", numberOfNights=" + numberOfNights +
                ", typeOfTour='" + typeOfTour + '\'' +
                ", departureLocation='" + departureLocation + '\'' +
                ", schedules=" + schedules.size() +
                '}';
    }
}
