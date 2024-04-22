package com.adventour.web.dto;

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
public class BookingDto {
    private Long id;
    private LocalDateTime bookingDate;
    private String [] imagesUrl;
    private String status;
    private int numberOfPassengers;
    private int totalAmount;
    private int amountPaid;
}
