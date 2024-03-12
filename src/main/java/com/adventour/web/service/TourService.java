package com.adventour.web.service;

import com.adventour.web.dto.TourDto;

import java.util.List;

public interface TourService {
    List<TourDto> findAllTours();
}
