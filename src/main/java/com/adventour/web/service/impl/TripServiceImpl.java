package com.adventour.web.service.impl;

import com.adventour.web.dto.TripDto;
import com.adventour.web.models.Trip;
import com.adventour.web.repository.TripRepository;
import com.adventour.web.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;

public class TripServiceImpl implements TripService {
    private TripRepository tripRepository;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository){
        this.tripRepository = tripRepository;
    }

    public TripDto mapToTripDto(Trip trip){
        TripDto tripDto = new TripDto();
        tripDto.setId(trip.getId());
        tripDto.setTypeOfTrip(trip.getTypeOfTrip());
        tripDto.setSlots(trip.getSlots());
        tripDto.setStartDate(trip.getStartDate());
        tripDto.setEndDate(trip.getEndDate());
        tripDto.setMinPassenger(trip.getMinPassenger());
        tripDto.setActualPassenger(trip.getActualPassenger());
        tripDto.setPriceTicket(trip.getPriceTicket());
        tripDto.setTour(trip.getTour());
        //tripDto.setSchedules(trip.getSchedules());

        return tripDto;
    }
    public Trip mapToTrip(TripDto trip){
        Trip tripDto = new Trip();
        tripDto.setId(trip.getId());
        tripDto.setTypeOfTrip(trip.getTypeOfTrip());
        tripDto.setSlots(trip.getSlots());
        tripDto.setStartDate(trip.getStartDate());
        tripDto.setEndDate(trip.getEndDate());
        tripDto.setMinPassenger(trip.getMinPassenger());
        tripDto.setActualPassenger(trip.getActualPassenger());
        tripDto.setPriceTicket(trip.getPriceTicket());
        tripDto.setTour(trip.getTour());
        //tripDto.setSchedules(trip.getSchedules());

        return tripDto;
    }

    @Override
    public void addNewTrip(Long tourId, TripDto tripDto) {

    }
}
