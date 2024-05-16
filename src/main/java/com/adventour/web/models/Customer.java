package com.adventour.web.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
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
