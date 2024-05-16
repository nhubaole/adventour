package com.adventour.web.service.impl;

import com.adventour.web.InvalidDataException;
import com.adventour.web.dto.PassengerDto;
import com.adventour.web.dto.TripDto;
import com.adventour.web.models.Passenger;
import com.adventour.web.models.Tour;
import com.adventour.web.models.Trip;
import com.adventour.web.repository.TourRepository;
import com.adventour.web.repository.TripRepository;
import com.adventour.web.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TripServiceImpl implements TripService {
    private final TripRepository tripRepository;
    private final TourRepository tourRepository;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository, TourRepository tourRepository){
        this.tripRepository = tripRepository;
        this.tourRepository = tourRepository;
    }


    @Override
    public Trip addNewTrip(TripDto tripDto, Long idTour){
        try {
            if (validateTrip(tripDto)){
                Trip trip = mapToTrip(tripDto);
                Tour tour = tourRepository.findById(idTour).orElse(null);
                trip.setTour(tour);
                int price = tour.getEstimatedPrice();
                if(Objects.equals(tripDto.getTypeOfTrip(), "Khuyến mãi")){
                    trip.setPriceTicket((int) (price - price * trip.getDiscount()*0.01));
                }
                else {
                    trip.setPriceTicket(price);
                }
                trip = tripRepository.save(trip);
                return trip;
            }
        } catch (InvalidDataException ex){

        }
        return null;
    }

    @Override
    public Trip editTrip(TripDto tripDto) {
        if(validateTrip(tripDto)){
            return tripRepository.save(mapToTrip(tripDto));
        }
        return  null;
    }

    @Override
    public List<TripDto> getListTrip() {
        List<Trip> trips = tripRepository.findAll();
        return trips.stream().map(trip -> mapToTripDto(trip)).collect(Collectors.toList());
    }

    @Override
    public TripDto getTripDetail(Long id) {
        Trip trip = tripRepository.findById(id).orElse(null);
        return trip!= null ? mapToTripDto(trip) : null;
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
        if(tripDto.getActualPassenger() > 0  ){

        }

    }

    public boolean validateTrip (TripDto tripDto) throws InvalidDataException {
        if(tripDto.getTour().getId() != null
            && !tripDto.getTypeOfTrip().isEmpty()
            && tripDto.getStartDate() != null
            && tripDto.getEndDate() != null
            && !tripDto.getStartDate().isAfter(tripDto.getEndDate())) {
            return true;
        }
        throw new InvalidDataException("Invalid Data");
    }

    public TripDto mapToTripDto(Trip trip){
        TripDto tripDto = new TripDto();
        tripDto.setId(trip.getId());
        tripDto.setSlots(trip.getSlots());
        tripDto.setStartDate(trip.getStartDate());
        tripDto.setEndDate(trip.getEndDate());
        tripDto.setPriceTicket(trip.getPriceTicket());
        tripDto.setDiscount(trip.getDiscount());
        tripDto.setTour(trip.getTour());
        tripDto.setPassengers(trip.getPassengers());
        return tripDto;
    }
    public Trip mapToTrip(TripDto tripDto){
        Trip trip = new Trip();
        trip.setId(tripDto.getId());
        trip.setSlots(tripDto.getSlots());
        trip.setStartDate(tripDto.getStartDate());
        trip.setEndDate(tripDto.getEndDate());
        trip.setPriceTicket(trip.getPriceTicket());
        trip.setDiscount(tripDto.getDiscount());
        trip.setTour(tripDto.getTour());
        trip.setPassengers(tripDto.getPassengers());
        return trip;
    }
}
