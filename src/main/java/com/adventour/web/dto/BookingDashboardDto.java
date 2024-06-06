package com.adventour.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDashboardDto {
    String imageUrl;
    String tourName;
    int numberPass;
    String typeOfTour;
    String statusOfBooking;

    public PaymentDashboardDto paymentDashboardDto;
    public TripDto tripDto;
}
