package com.adventour.web.dto;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private Long id;

    private String code;

    @NotEmpty(message = "Name of customer can be empty")
    private String nameCustomer;

    @NotEmpty(message = "Phone of customer can be empty")
    private String phoneNumber;

    @NotEmpty(message = "Email of customer can be empty")
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    private LocalDate dateOfBirth;

    @NotEmpty(message = "CCCD of customer can be empty")
    @Size(min = 12, max = 12, message = "CCCD must have exactly 12 characters")
    private String cccd;

    private Boolean isMale;
    private String nationality;
    private String addressCustomer;
    private String[] imagesCustomer;

    private MultipartFile[] files;

    private String customerID;

//    @NotNull
//    private PaymentInformationDto paymentInformationDto;
//
//    @NotNull
//    private TicketDto ticketDto;
//
//    @NotNull
//    private BookingDto bookingDto;
}
