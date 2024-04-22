package com.adventour.web.service.impl;

import com.adventour.web.dto.LocationDto;
import com.adventour.web.models.Location;
import com.adventour.web.repository.LocationRepository;
import com.adventour.web.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class LocationServiceImpl implements  LocationService{
    private LocationRepository locationRepository;

    @Autowired
    public LocationServiceImpl (LocationRepository locationRepository){
        this.locationRepository = locationRepository;
    }

    private LocationDto mapToLocationDto(Location location) {
        return LocationDto.builder()
                .id(location.getId())
                .nameLocation(location.getNameLocation())
                .address(location.getAddress())
                .description(location.getDescription())
                .images(location.getImages())
                .build();
    }

    @Override
    public List<LocationDto> findAllLocation() {

        List<Location> locations = locationRepository.findAll();
        return  locations.stream().map(location -> mapToLocationDto(location)).collect(Collectors.toList());
    }
}
