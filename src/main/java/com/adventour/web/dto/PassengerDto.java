package com.adventour.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDto {
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

}
