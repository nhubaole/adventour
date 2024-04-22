package com.adventour.web.dto;

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
    private Long idPassenger;
    private String statusTicket;
    private LocalDateTime datePaid;
    private int price;
    private String typeTicket;//lớn hay con nít
    private String mainImageOfTour;//hển th hiình ảnh của chueyesn trên vé
}
