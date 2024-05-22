package com.adventour.web.service;

import com.adventour.web.dto.PassengerDto;
import com.adventour.web.models.Passenger;

import java.util.List;
import java.util.Set;

public interface PassengerService {

    public Passenger addNewPassenger (PassengerDto passengerDto);
    public Passenger editPassenger (PassengerDto passengerDto);

    public Set<PassengerDto> getPassengersByIdBooking (Long idBooking);

    public  void deletePassenger (PassengerDto passengerDto);



}
