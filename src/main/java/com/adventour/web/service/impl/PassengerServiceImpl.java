package com.adventour.web.service.impl;

import com.adventour.web.dto.PassengerDto;
import com.adventour.web.models.Passenger;
import com.adventour.web.repository.PassengerRepository;
import com.adventour.web.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;

public class PassengerServiceImpl implements PassengerService {
    private PassengerRepository passengerRepository;

    @Autowired
    private PassengerServiceImpl(PassengerRepository passengerRepository){
        this.passengerRepository = passengerRepository;
    }

    private PassengerDto mapToPassengerDto(Passenger passenger){
        return PassengerDto.builder()
                .id(passenger.getId())
                .namePassenger(passenger.getNamePassenger())
                .dateOfBirth(passenger.getDateOfBirth())
                .cccd(passenger.getCccd())
                .isMale(passenger.isMale())
                .build();
    }
}
