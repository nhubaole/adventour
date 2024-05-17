package com.adventour.web.service;

import com.adventour.web.dto.PassengerDto;
import com.adventour.web.dto.PaymentInformationDto;
import com.adventour.web.models.Passenger;
import com.adventour.web.models.PaymentInformation;

import java.util.Set;

public interface PaymentInformationService {
    public PaymentInformation addNewPassenger (PassengerDto passengerDto);
    public PaymentInformation editPassenger (PassengerDto passengerDto);

    public Set<PaymentInformationDto> getPaymentInforByIdBooking (Long idBooking);
}
