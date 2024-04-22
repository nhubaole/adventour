package com.adventour.web.service;

import com.adventour.web.dto.TourDto;
import com.adventour.web.models.Tour;

import java.util.List;

public interface TourService {
    List<TourDto> findAllTours();
    TourDto findByTourId(long tourId);
    Tour saveTour(Tour tour);
}
