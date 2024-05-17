package com.adventour.web.service.impl;

import com.adventour.web.dto.PassengerDto;
import com.adventour.web.dto.PaymentInformationDto;
import com.adventour.web.models.PaymentInformation;
import com.adventour.web.repository.PassengerRepository;
import com.adventour.web.repository.PaymentInformationRepository;
import com.adventour.web.service.PaymentInformationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class PaymentInformationServiceImpl implements PaymentInformationService {
    private PaymentInformationRepository paymentInformationRepository;

    @Autowired
    private PaymentInformationServiceImpl(PaymentInformationRepository paymentInformationRepository){
        this.paymentInformationRepository = paymentInformationRepository;
    }



    @Override
    public PaymentInformation addNewPassenger(PassengerDto passengerDto) {
        return null;
    }

    @Override
    public PaymentInformation editPassenger(PassengerDto passengerDto) {
        return null;
    }

    @Override
    public Set<PaymentInformationDto> getPaymentInforByIdBooking(Long idBooking) {
        return null;
    }
}
