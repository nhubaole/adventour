package com.adventour.web.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
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

    @ManyToOne
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
}
