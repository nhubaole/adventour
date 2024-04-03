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
    public Long id;
    public String nameCustomer;
    public String phoneNumber;
    public String email;
    public LocalDate dateOfBirth;
    public String cccd;
    public boolean isMale;
    public String nationality;
    public String addressCustomer;
    public String [] imagesCustomer;



}
