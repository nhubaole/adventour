package com.adventour.web.service;

import com.adventour.web.dto.TripDto;
import org.springframework.stereotype.Service;

@Service
public interface TripService {
    public void addNewTrip(Long tourId, TripDto tripDto);
}
