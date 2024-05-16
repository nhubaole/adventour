package com.adventour.web.service;

import com.adventour.web.dto.PassengerDto;
import com.adventour.web.dto.TripDto;
import com.adventour.web.models.Passenger;
import com.adventour.web.models.Trip;

import java.util.List;
import java.util.Set;

public interface TripService {
    public Trip addNewTrip(TripDto tripDto);
    public Trip editTrip(TripDto tripDto);

    public List<TripDto> getListTrip();

    public TripDto getTripDetail(Long id);

    public List<TripDto> searchTrip();

    public Set<Passenger> getTripPassenger(TripDto tripDto);

    public void deleteTrip(TripDto tripDto);

}
