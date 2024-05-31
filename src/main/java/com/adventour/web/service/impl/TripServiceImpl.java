package com.adventour.web.service.impl;

import com.adventour.web.dto.BookingDto;
import com.adventour.web.dto.PassengerDto;
import com.adventour.web.dto.TripDto;
import com.adventour.web.enums.StatusOfBooking;
import com.adventour.web.mapper.Mapper;
import com.adventour.web.models.Passenger;
import com.adventour.web.models.Tour;
import com.adventour.web.models.Trip;
import com.adventour.web.repository.TourRepository;
import com.adventour.web.repository.TripRepository;
import com.adventour.web.service.BookingService;
import com.adventour.web.service.TripService;
import com.adventour.web.utils.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class TripServiceImpl implements TripService {
    private final TripRepository tripRepository;
    private final TourRepository tourRepository;
    private final BookingService bookingService;

    private final Mapper mapper;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository, TourRepository tourRepository, BookingService bookingService, Mapper mapper){
        this.tripRepository = tripRepository;
        this.tourRepository = tourRepository;
        this.bookingService = bookingService;
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
        List<TripDto> tripDtos = new ArrayList<>();
        for(Trip trip: trips){
            TripDto tripDto = mapper.mapToTripDto(trip);
            tripDto.setActualPassenger(updateTripActualPas(tripDto.getId()));
            tripDtos.add(tripDto);
        }
        return tripDtos;

    }

    @Override
    public TripDto getTripDetail(Long id) {
        Trip trip = tripRepository.findById(id).orElse(null);
        if(trip !=null){
            TripDto tripDto = mapper.mapToTripDto(trip);
            tripDto.setActualPassenger(updateTripActualPas(tripDto.getId()));
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
    public Set<PassengerDto> getTripPassenger(TripDto tripDto) {
        Set<PassengerDto> passengers = new HashSet<>();
        List<BookingDto> bookingDtos = bookingService.getListBooking();
        for(BookingDto bookingDto : bookingDtos){
            passengers.addAll(bookingDto.getPassengerDtos());
        }
        return passengers;
    }

    @Override
    public void cancelTrip(TripDto tripDto) {
        tripDto.setStatus("CANCELLED");
        updateTrip(tripDto);
        List<BookingDto> bookingDtos = bookingService.getBookingsByTripId(tripDto.getId());
        for(BookingDto bookingDto : bookingDtos){
            bookingDto.setStatus(StatusOfBooking.CANCELLED);
            bookingService.updateBooking(bookingDto);
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

    public int updateTripActualPas (Long idTrip){
        int actual = 0;
        List<BookingDto> bookingDtoList = bookingService.getBookingsByTripId(idTrip);
        for(BookingDto bookingDto : bookingDtoList){
            if(bookingDto.getStatus() == StatusOfBooking.COMPLETED){
                actual += bookingDto.getNumberOfPassengers();
            }
        }
        return actual;
    }
}
