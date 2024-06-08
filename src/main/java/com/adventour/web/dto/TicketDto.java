package com.adventour.web.dto;

import com.adventour.web.enums.StatusOfTicket;
import com.adventour.web.models.Booking;
import com.adventour.web.models.Passenger;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketDto {
    private Long id;

    private StatusOfTicket statusTicket;

    private LocalDateTime usedAt;

    private String namePassenger;
    private String typeTicket;//lớn hay con nít
    private String mainImageOfTour;//hển th hiình ảnh của chueyesn trên vé
    private String code;

    private BookingDto bookingDto;
    private PassengerDto passengerDto;


}
