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

    private String cccd;

    private boolean isMale;

    private String type;

    @NotEmpty(message = "Date of birth of passenger can't be empty")
    private LocalDate dateOfBirth;

    private BookingDto bookingDto;

    private TripDto tripDto;

    public PassengerDto(String name, String cccd, boolean isMale, String type,  LocalDate dob) {
        this.namePassenger = name;
        this.cccd = cccd;
        this.isMale = isMale;
        this.type = type;
        this.dateOfBirth = dob;
    }


//    public PassengerDto(long l, String name, String number, String mail, LocalDate of, String number1, boolean b, String việtNam, String s, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, BookingDto booking, Object o6) {
//    }
//
//    public PassengerDto(String name, String number, String mail, LocalDate of, String number1, boolean b, String việtNam, String s, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, BookingDto booking, Object o6) {
//    }
}
