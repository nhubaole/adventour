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
@Table(name = "passengers")
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String namePassenger;
    private String phoneNumber;
    private String email;

    @CreationTimestamp
    private LocalDate dateOfBirth;

    private String cccd;
    private boolean isMale;
    private String nationality;
    private String addressPassenger;
    private String [] imagesPassenger;
    private String passport;

    @CreationTimestamp
    private LocalDateTime passportExpireDate;
    private String visa;

    @CreationTimestamp
    private LocalDateTime visaExpireDate;

    @ManyToOne
    @JoinColumn(name = "id_booking", nullable = false)
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "id_trip", nullable = false)
    private Trip trip;


}
