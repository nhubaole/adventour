package com.adventour.web.service.impl;

import com.adventour.web.dto.PassengerDto;
import com.adventour.web.mapper.Mapper;
import com.adventour.web.models.Booking;
import com.adventour.web.models.Passenger;
import com.adventour.web.repository.BookingRepository;
import com.adventour.web.repository.PassengerRepository;
import com.adventour.web.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service

public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;
    private  final Mapper mapper;

    @Autowired
    private PassengerServiceImpl(PassengerRepository passengerRepository, BookingRepository bookingRepository, Mapper mapper){
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
        this.mapper = mapper;
    }

    @Override
    public Passenger addNewPassenger(PassengerDto passengerDto) {
        Passenger passenger = mapper.mapToPassenger(passengerDto);
        return passengerRepository.save(passenger);
    }

    @Override
    public Passenger editPassenger(PassengerDto passengerDto) {
        Passenger passenger = mapper.mapToPassenger(passengerDto);
        return passengerRepository.save(passenger);
    }

    @Override
    public Set<PassengerDto> getPassengersByIdBooking(Long idBooking) {
        Booking booking = bookingRepository.findById( idBooking).orElse(null);
        if(booking != null){
            List<Passenger> passengers = passengerRepository.findByBooking(booking);
            return passengers.stream()
                    .map(mapper::mapToPassengerDto)
                    .collect(Collectors.toSet());
        }
        return null;
    }

    @Override
    public void deletePassenger(PassengerDto passengerDto) {

    }
}
