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
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @CreationTimestamp
    public LocalDateTime bookingDate;
    public String status;
    public int numberOfPassengers;
    public int totalAmount;
    public int amountPaid;
    public Long idTrip;
    public Long idAccount;
    public Long[] idPassengers;
    public Long[] idPaymentInfor;
}
