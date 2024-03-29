package com.adventour.web.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
public class TourDto {
    private Long id;
    private String tourName;
    private String departureLocation;
    private int estimatedPrice;
    private int numberOfDays;
    private int numberOfNights;
    private String tourType;
}
