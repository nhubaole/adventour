package com.adventour.web.dto;

import com.adventour.web.enums.PaymentMethod;
import com.adventour.web.models.Booking;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInformationDto {

    private Long id;

    @Min(value = 0)
    private int amountOfMoney;

    @NotNull
    private LocalDateTime paymentTime;

    //private String status;

    @NotNull
    private PaymentMethod paymentMethod;

    private BookingDto bookingDto;
}
