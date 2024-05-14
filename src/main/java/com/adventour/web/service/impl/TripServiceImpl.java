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
        return tripDto;
    }
}
