package com.adventour.web.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tours")
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tourName;
    private String departureLocation;
    private int estimatedPrice;
    private int numberOfDays;
    private int numberOfNights;
    private String typeOfTour;

    @CreationTimestamp
    private LocalDateTime createdAt;


    @OneToMany(mappedBy = "tour",cascade = CascadeType.REMOVE)
    private Set<Schedule> schedules = new HashSet<>();
}
