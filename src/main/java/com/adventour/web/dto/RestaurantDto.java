package com.adventour.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDto {
    private Long id;
    private String nameRestaurant;
    private String addressRes;
    private String phoneNumberRes;
    private String descriptionRes;
    private int capacity;

}
