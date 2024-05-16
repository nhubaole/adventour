package com.adventour.web.service.impl;

import com.adventour.web.dto.CustomerDto;
import com.adventour.web.dto.ScheduleDto;
import com.adventour.web.dto.TourDto;
import com.adventour.web.models.Customer;
import com.adventour.web.models.Tour;
import com.adventour.web.repository.TourRepository;
import com.adventour.web.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourServiceImpl implements TourService {
    private final TourRepository tourRepository;

    @Autowired
    public TourServiceImpl(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    @Override
    public List<TourDto> searchTour(String keyword) {
        keyword = keyword.trim();
        List<TourDto> result = new ArrayList<>();
        List<Tour> searchedTours = tourRepository.findAll();
        for (Tour tour : searchedTours){
            if (tour.getTourName().contains(keyword) ||
                    tour.getId().toString().contains(keyword) ||
                    tour.getDepartureLocation().contains(keyword) ||
                    tour.getTypeOfTour().contains(keyword) ){
                TourDto tourDto = mapToTourDto(tour);
                result.add(tourDto);
            }
        }
        return result;
    }

    @Override
    public List<TourDto> findAllTours() {
        List<Tour> tours = tourRepository.findAll();
        return tours.stream().map((tour) -> mapToTourDto(tour)).collect(Collectors.toList());
    }

    @Override
    public TourDto findByTourId(long tourId) {
        Tour tour = tourRepository.findById(tourId).get();
        return mapToTourDto(tour);
    }

    @Override
    public Tour saveTour(Tour tour) {
        return tourRepository.save(tour);
    }

    @Override
    public Tour findByJustTourId(long tourId) {
        Tour tour = tourRepository.findById(tourId).get();
        return tour;
    }

    @Override
    public List<ScheduleDto> getTourSchedule(long tourId) {
        return null;
    }

    @Override
    public Tour editTourDetail(TourDto tourDto) {
        Tour tour = maptoTour(tourDto);
        tourRepository.save(tour);
        return tour;
    }

    private Tour maptoTour(TourDto tourDto) {
        return Tour.builder()
                .id(tourDto.getId())
                .tourName(tourDto.getTourName())
                .departureLocation(tourDto.getDepartureLocation())
                .estimatedPrice(tourDto.getEstimatedPrice())
                .numberOfDays(tourDto.getNumberOfDays())
                .numberOfNights(tourDto.getNumberOfNights())
                .typeOfTour(tourDto.getTypeOfTour())
                .build();
    }

    @Override
    public Tour editTourSchedule(ScheduleDto scheduleDto) {
        return null;
    }

    @Override
    public boolean deleteTour(long tourId) {
        tourRepository.deleteById(tourId);
        return true;
    }

    private TourDto mapToTourDto(Tour tour) {
        return TourDto.builder()
                .id(tour.getId())
                .tourName(tour.getTourName())
                .departureLocation(tour.getDepartureLocation())
                .estimatedPrice(tour.getEstimatedPrice())
                .numberOfDays(tour.getNumberOfDays())
                .numberOfNights(tour.getNumberOfNights())
//                .typeOfTour(tour.getTypeOfTour())
                .build();
    }
}
