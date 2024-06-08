package com.adventour.web.service;

import com.adventour.web.dto.LocationDto;
import com.adventour.web.models.Location;

import java.util.List;

public interface LocationService {
    List<LocationDto> findAllLocation();

    List<LocationDto> searchLocation(String search);

    Location addNewLocation (LocationDto locationDto);

    LocationDto getLocationById(Long id);

    Location editLocation(LocationDto locationDto);

    boolean deleteLocation(Long locationId);





}
