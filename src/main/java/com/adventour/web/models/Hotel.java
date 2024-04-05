package com.adventour.web.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "hotels")
public class Hotel {
    private Long id;
    private String nameHotel;
    private String addressHotel;
    private String phoneNumber;
    private int star;
    private int capacity;

}
