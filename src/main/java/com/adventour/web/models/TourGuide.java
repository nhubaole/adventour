//package com.adventour.web.models;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.CreationTimestamp;
//
//import java.time.LocalDate;
//import java.util.HashSet;
//import java.util.Set;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Entity
//@Table(name = "tour_guides")
//public class TourGuide {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String nameTourGuide;
//    private boolean isMale;
//    @CreationTimestamp
//    private LocalDate dateOfBirth;
//    private String addressTourGuide;
//    private String phoneNumber;
//
//    @ManyToMany
//    @JoinTable(
//            name = "tour_manager",
//            joinColumns = @JoinColumn(name = "id_tour_guild"),
//            inverseJoinColumns = @JoinColumn(name = "id_trip"))
//    private Set<Trip> trips = new HashSet<>();
//
//
//
//}
