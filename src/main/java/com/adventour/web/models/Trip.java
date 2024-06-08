package com.adventour.web.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "trips")
public class Trip {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private int slots;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int priceTicket;

    @Column(nullable = true)
    private double discount;

    private String tripType;

    private String status;

    //các thng tin để dẽ truy xất, cập nhập
    @ManyToOne
    @JoinColumn(name = "id_tour", nullable = false )
    private Tour tour;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.REMOVE)
    private Set<Passenger> passengers = new HashSet<>();

//    private Set<Schedule> schedules = tour.getSchedules();

//    @ManyToMany(mappedBy = "trips")
//    private Set<TourGuide> tourGuides = new HashSet<>();

}
