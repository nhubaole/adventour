package com.adventour.web.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "passengers")
public class Passenger {

    private Long id;
    private String namePassenger;
    private String phoneNumber;
    private String email;
    private LocalDate dateOfBirth;
    private String cccd;
    private boolean isMale;
    private String nationality;
    private String addressPassenger;
    private String [] imagesPassenger;
    private String passport;
    private LocalDateTime passportExpireDate;
    private String visa;
    private LocalDateTime visaExpireDate;

    @ManyToOne
    @JoinColumn(name = "id_booking", nullable = false)
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "id_trip", nullable = false)
    private Trip trip;


}
