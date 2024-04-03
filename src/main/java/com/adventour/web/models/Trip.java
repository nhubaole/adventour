package com.adventour.web.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    public String tourName;
    public String typeOfTrip;
    public int slots;
    @CreationTimestamp
    public LocalDateTime startDate;
    @CreationTimestamp
    public LocalDateTime endDate;
    public int minPassenger;
    public int actualPassenger;


}
