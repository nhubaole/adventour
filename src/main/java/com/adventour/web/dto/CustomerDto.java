package com.adventour.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private Long id;
    private String nameCustomer;
    private String phoneNumber;
    private String email;
    private LocalDate dateOfBirth;
    private String cccd;
    private boolean isMale;
    private String nationality;
    private String addressCustomer;
    private String [] imagesCustomer;

}
