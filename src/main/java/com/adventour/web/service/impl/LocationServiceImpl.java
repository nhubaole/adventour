package com.adventour.web.service.impl;

import com.adventour.web.dto.LocationDto;
import com.adventour.web.mapper.Mapper;
import com.adventour.web.models.Location;
import com.adventour.web.repository.LocationRepository;
import com.adventour.web.repository.ScheduleRepository;
import com.adventour.web.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class LocationServiceImpl implements  LocationService{
    private final LocationRepository locationRepository;
    private final ScheduleRepository scheduleRepository;
    private final Mapper mapper;

    @Autowired
    public LocationServiceImpl (LocationRepository locationRepository, ScheduleRepository scheduleRepository, Mapper mapper){
        this.locationRepository = locationRepository;
        this.scheduleRepository = scheduleRepository;
        this.mapper = mapper;
    }

    @Override
    public List<LocationDto> findAllLocation() {

        List<Location> locations = locationRepository.findAll();
        return  locations.stream().map(location -> mapper.mapToLocationDto(location)).collect(Collectors.toList());
    }

    @Override
    public List<LocationDto> searchLocation(String search) {
        List<LocationDto> result = new ArrayList<>();
        search = search.trim();
         List<Location> locations = locationRepository.findAll();
         for(Location location : locations){
             if(location.getNameLocation().contains(search)){
                 result.add(mapper.mapToLocationDto(location));
             }
         }
        return result;
    }

    @Override
    public Location addNewLocation(LocationDto locationDto) {

        if(validateLocation(locationDto)){
            Location location = mapper.mapToLocation(locationDto);
            return locationRepository.save(location);
        }
        return null;
    }

    @Override
    public LocationDto getLocationById(Long id) {
        Location location = locationRepository.findById(id).orElse(null);
        if(location != null){
            return mapper.mapToLocationDto(location);
        }
        return null;
    }

    @Override
    public Location editLocation(LocationDto locationDto) {
        if(validateLocation(locationDto)){
            Location location = mapper.mapToLocation(locationDto);
            return locationRepository.save(location);
        }
        return null;
    }

    @Override
    public boolean deleteLocation(Long locationId) {
        Location location = locationRepository.findById(locationId).orElse(null);
        if(location != null){
            if(scheduleRepository.findByStartLocation(location).isEmpty()
                    && scheduleRepository.findByEndLocation(location).isEmpty()){
                locationRepository.deleteById(locationId);
                return true;
            }
        }
        return false;
    }

    public boolean validateLocation(LocationDto locationDto){
        if(!locationDto.getNameLocation().trim().isEmpty() && !locationDto.getAddress().trim().isEmpty() && !locationDto.getDescription().trim().isEmpty()){
            return true;
        }
        return false;
    }
}
