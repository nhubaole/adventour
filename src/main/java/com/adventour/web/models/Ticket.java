package com.adventour.web.models;

import com.adventour.web.enums.StatusOfTicket;
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
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private StatusOfTicket statusTicket;

    @CreationTimestamp
    private LocalDateTime datePaid;

    private int price;
    private String typeTicket;//lớn hay con nít
    private String mainImageOfTour;//hển th hiình ảnh của chueyesn trên vé

    @ManyToOne
    @JoinColumn(name = "id_booking", nullable = false)
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "id_passenger", nullable = false)
    private Passenger passenger;





}
