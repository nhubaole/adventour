package com.adventour.web.service.impl;

import com.adventour.web.dto.PaymentInformationDto;
import com.adventour.web.models.PaymentInformation;
import com.adventour.web.repository.PassengerRepository;
import com.adventour.web.repository.PaymentInformationRepository;
import com.adventour.web.service.PaymentInformationService;
import org.springframework.beans.factory.annotation.Autowired;

public class PaymentInformationServiceImpl implements PaymentInformationService {
    private PaymentInformationRepository paymentInformationRepository;

    @Autowired
    private PaymentInformationServiceImpl(PaymentInformationRepository paymentInformationRepository){
        this.paymentInformationRepository = paymentInformationRepository;
    }

    private PaymentInformationDto mapToPaymentInformationDto(PaymentInformation paymentInformation){
        return  PaymentInformationDto.builder()
                .id(paymentInformation.getId())
                .amountOfMoney(paymentInformation.getAmountOfMoney())
                .status(paymentInformation.getStatus())
                .paymentMethod(paymentInformation.getPaymentMethod())
                .paymentTime(paymentInformation.getPaymentTime())
                .build();
    }
}
