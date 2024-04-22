package com.adventour.web.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private LocalDateTime bookingDate;
    private String [] imagesUrl;
    private String status;
    private int numberOfPassengers;
    private int totalAmount;
    private int amountPaid;

    @ManyToOne
    @JoinColumn(name="id_trip", nullable=false)
    private Trip trip;

    @ManyToOne
    @JoinColumn(name="id_customer", nullable=false)
    private Customer customer;


    @OneToMany(mappedBy = "booking")
    private Set<Passenger> passengers = new HashSet<>();

    @OneToMany(mappedBy = "booking")
    private Set<PaymentInformation> paymentInformation;

    //1 booking => nhều vé
    @OneToMany(mappedBy = "booking")
    private Set<Ticket> tickets;
}
