package com.adventour.web.service.impl;

import com.adventour.web.dto.PassengerDto;
import com.adventour.web.dto.PaymentInformationDto;
import com.adventour.web.mapper.Mapper;
import com.adventour.web.models.Booking;
import com.adventour.web.models.PaymentInformation;
import com.adventour.web.repository.BookingRepository;
import com.adventour.web.repository.PaymentInformationRepository;
import com.adventour.web.service.PaymentInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PaymentInformationServiceImpl implements PaymentInformationService {
    private final PaymentInformationRepository paymentInformationRepository;
    private final BookingRepository bookingRepository;

    private final Mapper mapper;

    @Autowired
    private PaymentInformationServiceImpl(PaymentInformationRepository paymentInformationRepository, BookingRepository bookingRepository, Mapper mapper){
        this.paymentInformationRepository = paymentInformationRepository;
        this.bookingRepository = bookingRepository;
        this.mapper = mapper;
    }

    @Override
    public PaymentInformation addNewPaymentInformation(PaymentInformationDto paymentInformationDto) {
        if(validatePayment(paymentInformationDto)){
            PaymentInformation paymentInformation = mapper.mapToPaymentInformation(paymentInformationDto);
            return paymentInformationRepository.save(paymentInformation);
        }
        return null;
    }

    @Override
    public PaymentInformation eidtPaymentInformation(PaymentInformationDto paymentInformationDto) {
        if(validatePayment(paymentInformationDto)){
            PaymentInformation paymentInformation = mapper.mapToPaymentInformation(paymentInformationDto);
            return paymentInformationRepository.save(paymentInformation);
        }
        return null;
    }

    @Override
    public Set<PaymentInformationDto> getPaymentInforByIdBooking(Long idBooking) {

        Booking booking = bookingRepository.findById(idBooking).orElse(null);
        if(booking != null){
            Set<PaymentInformation> paymentInformations = paymentInformationRepository.findByBooking(booking);

            return paymentInformations.stream().map(mapper::mapToPaymentInformationDto).collect(Collectors.toSet());
        }
        return null;
    }

    @Override
    public void deletePaymentInfor(PaymentInformationDto paymentInformationDto) {

    }

    public boolean validatePayment (PaymentInformationDto paymentInformationDto){
        if(paymentInformationDto.getPaymentTime() != null
        && paymentInformationDto.getPaymentMethod() != null
        && paymentInformationDto.getAmountOfMoney() > 0
        && paymentInformationDto.getBookingDto() != null)
        {
            return true;
        }
        return false;
    }
}
