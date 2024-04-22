package com.adventour.web.service;

import com.adventour.web.dto.LocationDto;

import java.util.List;

public interface LocationService {
    List<LocationDto> findAllLocation();

}
