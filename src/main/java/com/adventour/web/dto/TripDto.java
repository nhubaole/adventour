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
public class TripDto {
    public Long id;
    public int slots;
    public LocalDateTime startDate;
    public LocalDateTime endDate;
    public int minPassenger;
    public int actualPassenger;

}
