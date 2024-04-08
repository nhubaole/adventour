package com.adventour.web.dto;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jdk.jfr.Name;
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

    @NotEmpty(message = "Name of customer can be empty")
    private String nameCustomer;

    @NotEmpty(message = "Phone of customer can be empty")
    private String phoneNumber;

    @NotEmpty(message = "Email of customer can be empty")
    private String email;

    private LocalDate dateOfBirth;

    @NotEmpty(message = "CCCD of customer can be empty")
    private String cccd;

    private boolean isMale;
    private String nationality;
    private String addressCustomer;
    private String [] imagesCustomer;

}
