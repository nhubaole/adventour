package com.adventour.web.service;

import com.adventour.web.dto.PassengerDto;
import com.adventour.web.dto.PaymentInformationDto;
import com.adventour.web.models.Booking;
import com.adventour.web.models.PaymentInformation;

import java.util.Set;

public interface PaymentInformationService {
    public PaymentInformation addNewPaymentInformation (PaymentInformationDto paymentInformationDto);
    public PaymentInformation eidtPaymentInformation (PaymentInformationDto paymentInformationDto);

    public Set<PaymentInformationDto> getPaymentInforByBooking(Booking booking);
    public Set<PaymentInformationDto> getPaymentInforByIdBooking(Long idBooking);
    public void deletePaymentInfor(PaymentInformationDto paymentInformationDto);
}
