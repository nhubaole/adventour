package com.adventour.web.service.impl;

import com.adventour.web.utils.InvalidDataException;
import com.adventour.web.dto.TripDto;
import com.adventour.web.mapper.Mapper;
import com.adventour.web.models.Passenger;
import com.adventour.web.models.Tour;
import com.adventour.web.models.Trip;
import com.adventour.web.repository.TourRepository;
import com.adventour.web.repository.TripRepository;
import com.adventour.web.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TripServiceImpl implements TripService {
    private final TripRepository tripRepository;
    private final TourRepository tourRepository;

    private final Mapper mapper;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository, TourRepository tourRepository, Mapper mapper){
        this.tripRepository = tripRepository;
        this.tourRepository = tourRepository;
        this.mapper = mapper;
    }


    @Override
    public Trip addNewTrip(TripDto tripDto){
        try {
            if (validateTrip(tripDto)){
                Trip trip = mapper.mapToTrip(tripDto);
                Long idTour = tripDto.getTourDto().getId();
                Tour tour = tourRepository.findById(idTour).orElse(null);

                trip.setTour(tour);
                int price = tour.getEstimatedPrice();
                if(trip.getDiscount() > 0){
                    trip.setTripType("Khuyến mãi");
                }
                else {
                    trip.setTripType("Thông thường");
                }
                trip.setPriceTicket((int) (price - price * trip.getDiscount()*0.01));

                trip = tripRepository.save(trip);
                return trip;
            }
        } catch (InvalidDataException ex){

        }
        return null;
    }

    @Override
    public Trip editTrip(TripDto tripDto) {
        if (validateTrip(tripDto)){
            Trip trip = mapper.mapToTrip(tripDto);

            int price = tripDto.getTourDto().getEstimatedPrice();
            trip.setPriceTicket((int) (price - price * trip.getDiscount()*0.01));

            trip = tripRepository.save(trip);
            return trip;
        }
        return  null;
    }

    @Override
    public List<TripDto> getListTrip() {
        List<Trip> trips = tripRepository.findAll();
        return trips.stream().map(trip -> mapper.mapToTripDto(trip)).collect(Collectors.toList());
    }

    @Override
    public TripDto getTripDetail(Long id) {
        Trip trip = tripRepository.findById(id).orElse(null);
        return trip!= null ? mapper.mapToTripDto(trip) : null;
    }

    @Override
    public List<TripDto> searchTrip() {
        return null;
    }

    @Override
    public Set<Passenger> getTripPassenger(TripDto tripDto) {
        return tripDto.getPassengers();
    }

    @Override
    public void deleteTrip(TripDto tripDto) {
        if(tripDto.getActualPassenger() > 0 ){

        }

    }

    @Override
    public Set<TripDto> getTripByIdTour(Long idTour) {
        Tour tou = tourRepository.findById(idTour).get();
        Set<Trip> trips = tripRepository.findByTour(tou);
        Set<TripDto> tripDtos = new HashSet<>();
        for(Trip trip : trips){
            tripDtos.add(mapper.mapToTripDto(trip));
        }
        return tripDtos;
    }

    public boolean validateTrip (TripDto tripDto) throws InvalidDataException {
        if(tripDto.getTourDto().getId() != null
            && tripDto.getStartDate() != null
            && tripDto.getEndDate() != null
            && !tripDto.getStartDate().isAfter(tripDto.getEndDate())) {
            return true;
        }
        throw new InvalidDataException("Invalid Data");
    }
}
