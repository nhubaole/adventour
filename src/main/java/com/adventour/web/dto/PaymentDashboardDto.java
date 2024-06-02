package com.adventour.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
public class PaymentDashboardDto {
    String nameCustomer;
    int paidAmount;
    LocalDateTime datePaid;

    public PaymentDashboardDto(){};
    public PaymentDashboardDto(String nameCustomer, int paidAmount, LocalDateTime datePaid){
        this.nameCustomer = nameCustomer;
        this.paidAmount = paidAmount;
        this.datePaid = datePaid;
    }
}
