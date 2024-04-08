package com.adventour.web.dto;

import com.adventour.web.models.Location;
import com.adventour.web.models.Tour;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto {
    private Long id;

    private int dayOfSchedule;

    @NotNull
    private Tour tour;

    private Location startLocation;
    private Tour endLocation;
    private Set<Location> locations;
    private Set<Location> hotels;
    private Set<Location> restaurants;
    private Set<Location> vehicles;
    private Set<Location> otherServices;


}
