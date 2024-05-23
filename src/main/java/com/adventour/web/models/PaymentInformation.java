package com.adventour.web.models;

import com.adventour.web.enums.PaymentMethod;
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
@Table(name = "payment_information")
public class PaymentInformation {
    @Id
    @GeneratedValue (strategy =  GenerationType.IDENTITY)
    private Long id;
    private int amountOfMoney;
    @CreationTimestamp
    private LocalDateTime paymentTime;
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "id_booking", nullable = false)
    private Booking booking;

}
