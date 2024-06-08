package com.adventour.web.models;

import com.adventour.web.dto.*;
import com.adventour.web.enums.StatusOfBooking;
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
    private StatusOfBooking status;
    private int totalAmount;

    private int numberAdult;
    private int numberChildren;
    private int numberBaby;

    @ManyToOne
    @JoinColumn(name="id_trip", nullable=false)
    private Trip trip;

    @ManyToOne
    @JoinColumn(name="id_customer", nullable=false)
    private Customer customer;

    @OneToMany(mappedBy = "booking")
    private Set<Passenger> passengers = new HashSet<>();

    @OneToMany(mappedBy = "booking")
    private Set<PaymentInformation> paymentInformation = new HashSet<>();

    //1 booking => nhều vé
    @OneToMany(mappedBy = "booking")
    private Set<Ticket> tickets = new HashSet<>();

}
