package com.adventour.web.service.impl;

import com.adventour.web.dto.BookingDto;
import com.adventour.web.dto.PaymentInformationDto;
import com.adventour.web.models.Booking;
import com.adventour.web.models.PaymentInformation;
import com.adventour.web.repository.BookingRepository;
import com.adventour.web.repository.PassengerRepository;
import com.adventour.web.repository.PaymentInformationRepository;
import com.adventour.web.service.PaymentInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentInformationServiceImpl implements PaymentInformationService {
    private PaymentInformationRepository paymentInformationRepository;


    @Autowired
    private PaymentInformationServiceImpl(PaymentInformationRepository paymentInformationRepository, BookingRepository bookingRepository){
        this.paymentInformationRepository = paymentInformationRepository;

    }

    private PaymentInformationDto mapToPaymentInformationDto(PaymentInformation paymentInformation){
        return  PaymentInformationDto.builder()
                .id(paymentInformation.getId())
                .amountOfMoney(paymentInformation.getAmountOfMoney())
                .paymentMethod(paymentInformation.getPaymentMethod())
                .paymentTime(paymentInformation.getPaymentTime())
                .build();
    }




}
