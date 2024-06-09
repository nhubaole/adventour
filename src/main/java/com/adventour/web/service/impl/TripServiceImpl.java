package com.adventour.web.service.impl;

import com.adventour.web.dto.PassengerDto;
import com.adventour.web.dto.TripDto;
import com.adventour.web.enums.StatusOfBooking;
import com.adventour.web.mapper.Mapper;
import com.adventour.web.models.Booking;
import com.adventour.web.models.Passenger;
import com.adventour.web.models.Tour;
import com.adventour.web.models.Trip;
import com.adventour.web.repository.BookingRepository;
import com.adventour.web.repository.PassengerRepository;
import com.adventour.web.repository.TourRepository;
import com.adventour.web.repository.TripRepository;
import com.adventour.web.service.TripService;
import com.adventour.web.utils.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service

public class TripServiceImpl implements TripService {
    private final TripRepository tripRepository;
    private final TourRepository tourRepository;
    private final BookingRepository bookingRepository;
    private final PassengerRepository passengerRepository;
    private final Mapper mapper;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository, TourRepository tourRepository, BookingRepository bookingRepository, PassengerRepository passengerRepository, Mapper mapper){
        this.tripRepository = tripRepository;
        this.tourRepository = tourRepository;
        this.bookingRepository = bookingRepository;
        this.passengerRepository = passengerRepository;
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
    public Trip updateTrip(TripDto tripDto) {
        if (validateTrip(tripDto)){
            Trip trip = mapper.mapToTrip(tripDto);

            int price = tripDto.getTourDto().getEstimatedPrice() == null ? 0 : tripDto.getTourDto().getEstimatedPrice();
            trip.setPriceTicket((int) (price - price * trip.getDiscount()*0.01));

            trip = tripRepository.save(trip);
            return trip;
        }
        return  null;
    }

    @Override
    public List<TripDto> getListTrip() {
        List<Trip> trips = tripRepository.findAll();
        List<TripDto> tripDtos = new ArrayList<>();
        for(Trip trip: trips){
            TripDto tripDto = mapper.mapToTripDto(trip);
            tripDto.setActualPassenger(updateTripActualPas(trip));
            tripDtos.add(tripDto);
        }
        return tripDtos;

    }

    @Override
    public TripDto getTripDetail(Long id) {
        Trip trip = tripRepository.findById(id).orElse(null);
        if(trip !=null){
            TripDto tripDto = mapper.mapToTripDto(trip);
            tripDto.setActualPassenger(updateTripActualPas(trip));
            return tripDto;
        }
        return null;
    }

    @Override
    public List<TripDto> searchTrip(String search) {
        List<TripDto> allTrip = getListTrip();
        List<TripDto> result = new ArrayList<>();
        for(TripDto tripDto: allTrip){
            if(tripDto.getName().toLowerCase().contains(search.toLowerCase())
                || String.valueOf(tripDto.getId()).contains(search.toLowerCase())) {
                result.add(tripDto);
            }
        }

        return result;
    }

    @Override
    public List<PassengerDto> getTripPassenger(TripDto tripDto) {
        //TODO : Sai
        Trip trip = tripRepository.findById(tripDto.getId()).orElse(null);
        List<Passenger> passengers = passengerRepository.findByTrip(trip);

        return passengers.stream().map(mapper::mapToPassengerDto).collect(Collectors.toList());
    }

    @Override
    public void cancelTrip(TripDto tripDto) {
        tripDto.setStatus("CANCELLED");
        updateTrip(tripDto);
        Trip trip = tripRepository.findById(tripDto.getId()).orElse(null);
        List<Booking> bookings = bookingRepository.findByTrip(trip);
        for(Booking booking : bookings){
            booking.setStatus(StatusOfBooking.CANCELLED);
            bookingRepository.save(booking);
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
            && tripDto.getStartDate().isBefore(tripDto.getEndDate())) {
            return true;
        }
        throw new InvalidDataException("Invalid Data");
    }

    public int updateTripActualPas (Trip trip){
        //TODO : test laij
        int actual = 0;
        List<Booking> bookingList = bookingRepository.findByTrip(trip);
        for(Booking booking : bookingList){
            if(booking.getStatus() == StatusOfBooking.COMPLETED){
                actual += (booking.getNumberAdult() + booking.getNumberChildren() + booking.getNumberBaby());
            }
        }
        return actual;
    }
}
