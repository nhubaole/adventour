package com.adventour.web.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "paymentInformation")
public class PaymentInformation {
    @Id
    @GeneratedValue (strategy =  GenerationType.IDENTITY)
    public Long id;
    public Long idBooking;
    public int amountOfMoney;
    @CreationTimestamp
    public LocalDateTime paymentTime;
    public String status;
    public String paymentMethod;


}
