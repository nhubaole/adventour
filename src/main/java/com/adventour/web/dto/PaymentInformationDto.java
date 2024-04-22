package com.adventour.web.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInformationDto {

    public Long id;
    public Long idBooking;
    public int amountOfMoney;
    public LocalDateTime paymentTime;
    public String status;
    public String paymentMethod;
}
