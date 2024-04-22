package com.adventour.web.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table (name = "type_of_tour")
public class TypeOfTour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameTypeOfTour;
    private int buyBeforeDeparture;
    private int timeForFreeTicketRefund;
    private int feeLateRefund;
}
