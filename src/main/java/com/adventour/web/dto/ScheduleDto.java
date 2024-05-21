package com.adventour.web.dto;

import com.adventour.web.models.Location;
import com.adventour.web.models.Tour;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto {
    private Long id;
    private int dayOfSchedule;
    private LocationDto startLocation;
    private LocationDto endLocation;
    private Set<Location> locations;
    private List<String> hotels;
    private List<String> restaurants;
    private List<String> vehicles;
    private List<String> otherServices;
    private TourDto tour;

    @Override
    public String toString() {
        return "ScheduleDto{" +
                "id=" + id +
                ", startLocation='" + startLocation + '\'' +
                ", endLocation='" + endLocation + '\'' +
                ", dayOfSchedule=" + dayOfSchedule +
                ", tour=" + (tour != null ? tour.getId() : null) +
                '}';
    }
}
