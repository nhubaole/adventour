package com.adventour.web.service.impl;

import com.adventour.web.controller.LoginController;
import com.adventour.web.dto.PassengerDto;
import com.adventour.web.dto.PaymentInformationDto;
import com.adventour.web.enums.PaymentMethod;
import com.adventour.web.mapper.Mapper;
import com.adventour.web.models.Booking;
import com.adventour.web.models.PaymentInformation;
import com.adventour.web.repository.BookingRepository;
import com.adventour.web.repository.PaymentInformationRepository;
import com.adventour.web.service.PaymentInformationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service

public class PaymentInformationServiceImpl implements PaymentInformationService {
    private final PaymentInformationRepository paymentInformationRepository;
    private final BookingRepository bookingRepository;

    private final Mapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(PaymentInformationServiceImpl.class);


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
    public Set<PaymentInformationDto> getPaymentInforByBooking(Booking booking) {
        Set<PaymentInformation> paymentInformations = paymentInformationRepository.findByBooking(booking);
        return paymentInformations.stream().map(mapper::mapToPaymentInformationDto).collect(Collectors.toSet());
    }

    @Override
    public Set<PaymentInformationDto> getPaymentInforByIdBooking(Long idBooking) {
        //VietHam
        Set<PaymentInformationDto> paymentInformationDtos = new HashSet<>();
        logger.info("___________________________________________________________________");
        Set<Object[]> results = paymentInformationRepository.findByIdBooking(idBooking);
        for(Object[] row : results){
            long id = (long) row[0];
            int amountMoney = (int) row[1];
            LocalDateTime date  = (LocalDateTime) row[2];
            PaymentMethod paymentMethod = (PaymentMethod) row[3];

            PaymentInformationDto paymentInformationDto = new PaymentInformationDto(id, amountMoney, date, paymentMethod, null);

            paymentInformationDtos.add(paymentInformationDto);
        }
        return paymentInformationDtos;

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
