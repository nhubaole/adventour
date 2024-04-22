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

//    @ManyToMany
//    @JoinTable(
//            name = "detail_location",
//            joinColumns = @JoinColumn(name = "id_schedule"),
//            inverseJoinColumns = @JoinColumn(name = "id_location"))
//    private Set<Location> locations = new HashSet<>();

    private List<String> listHotel;
    private List<String> listRestaurant;
    private List<String> listVehicle;
    private List<String> listOtherService;


//
//    @ManyToMany
//    @JoinTable(
//            name = "detail_hotel",
//            joinColumns = @JoinColumn(name = "id_schedule"),
//            inverseJoinColumns = @JoinColumn(name = "id_hotel"))
//    private Set<Hotel> hotels =  new HashSet<>();
//
//    @ManyToMany
//    @JoinTable(
//            name = "detail_restaurant",
//            joinColumns = @JoinColumn(name = "id_schedule"),
//            inverseJoinColumns = @JoinColumn(name = "id_restaurant"))
//    private Set<Restaurant> restaurants = new HashSet<>();
//
//
//    @ManyToMany
//    @JoinTable(
//            name = "detail_vehicle",
//            joinColumns = @JoinColumn(name = "id_schedule"),
//            inverseJoinColumns = @JoinColumn(name = "id_vehicle"))
//    private Set<Vehicle> vehicles = new HashSet<>();
//
//    @ManyToMany
//    @JoinTable(
//            name = "detail_other_service",
//            joinColumns = @JoinColumn(name = "id_schedule"),
//            inverseJoinColumns = @JoinColumn(name = "id_other_service"))
//    private Set<OtherService> otherServices = new HashSet<>();

}
