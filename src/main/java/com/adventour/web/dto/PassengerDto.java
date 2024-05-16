package com.adventour.web.dto;

import com.adventour.web.models.Booking;
import com.adventour.web.models.Trip;
import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "Name of passenger can't be empty")
    private String namePassenger;

    private String phoneNumber;
    private String email;

    @NotEmpty(message = "Date of birth of passenger can't be empty")
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

    private Booking booking;
    private Trip trip;


    public PassengerDto(long l, String name, String number, String mail, LocalDate of, String number1, boolean b, String việtNam, String s, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, BookingDto booking, Object o6) {
    }
}
