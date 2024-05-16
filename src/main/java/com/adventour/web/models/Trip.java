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
    private Long id;
    private int slots;
    @CreationTimestamp
    private LocalDateTime startDate;
    @CreationTimestamp
    private LocalDateTime endDate;
    private int minPassenger;
    private int actualPassenger;

    public String nameTrip;

    public int discountPrice;

    public String tripType;

    public int priceTrip;

    public String statusTrip;

    public int priceTripFinal = (discountPrice * priceTrip) / 100;

    private Long priceTicket;

    public String nameTrip;

    public int discountPrice;

    public String tripType;

    public int priceTrip;

    public String statusTrip;

    public int priceTripFinal = (discountPrice * priceTrip) / 100;

    //các thng tin để dẽ truy xất, cập nhập
    @ManyToOne
    @JoinColumn(name = "id_tour", nullable = false )
    private Tour tour;

    @ManyToOne
    @JoinColumn(name = "id_type_of_trip", nullable = false )
    private TypeOfTrip typeOfTrip;


    @OneToMany(mappedBy = "trip", cascade = CascadeType.REMOVE)
    private Set<Passenger> passengers = new HashSet<>();

//    private Set<Schedule> schedules = tour.getSchedules();

    @ManyToMany(mappedBy = "trips")
    private Set<TourGuide> tourGuides = new HashSet<>();


}
