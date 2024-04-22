package com.adventour.web.models;

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
    public Long id;
    public int slots;
    @CreationTimestamp
    public LocalDateTime startDate;
    @CreationTimestamp
    public LocalDateTime endDate;
    public int minPassenger;
    public int actualPassenger;

    //các thng tin để dẽ truy xất, cập nhập
    @ManyToOne
    @JoinColumn(name = "id_tour", nullable = false )
    private Tour tour;

    @ManyToOne
    @JoinColumn(name = "id_type_of_trip", nullable = false )
    private TypeOfTrip typeOfTrip;


    @OneToMany(mappedBy = "trip", cascade = CascadeType.REMOVE)
    private Set<Passenger> passengers = new HashSet<>();


//    @OneToMany(mappedBy = "trip", cascade = CascadeType.REMOVE)
//    private Set<Schedule> schedules = new HashSet<>();

    @ManyToMany(mappedBy = "trips")
    private Set<TourGuide> tourGuides;





}
