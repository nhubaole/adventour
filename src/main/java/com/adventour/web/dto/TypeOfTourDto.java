package com.adventour.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TypeOfTourDto {
    private Long id;

    @NotEmpty(message = "Name type of tour can't be empty")
    private String nameTypeOfTour;

    private int buyBeforeDeparture;

    private int timeForFreeTicketRefund;

    private int feeLateRefund;
}
