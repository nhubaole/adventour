package com.adventour.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelDto {
    private Long id;
    private String nameHotel;
    private String addressHotel;
    private String phoneNumber;
    private int star;
    private int capacity;

}
