package com.adventour.web.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "schedules")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int dayOfSchedule;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_tour", nullable = false)
    private Tour tour;

    @ManyToOne
    @JoinColumn(name = "id_start_location", nullable = true)
    private Location startLocation;

    @ManyToOne
    @JoinColumn(name = "id_end_location", nullable = true)
    private Location endLocation;

    private List<String> listHotel;
    private List<String> listRestaurant;
    private List<String> listVehicle;
    private List<String> listOtherService;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(id, schedule.id) &&
                dayOfSchedule == schedule.dayOfSchedule &&
                Objects.equals(tour, schedule.tour) &&
                Objects.equals(startLocation, schedule.startLocation) &&
                Objects.equals(endLocation, schedule.endLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dayOfSchedule, tour, startLocation, endLocation);
    }
}
