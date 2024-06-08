package com.adventour.web.service.impl;

import com.adventour.web.controller.TourController;
import com.adventour.web.dto.CustomerDto;
import com.adventour.web.dto.ScheduleDto;
import com.adventour.web.dto.TourDto;
import com.adventour.web.mapper.Mapper;
import com.adventour.web.models.Customer;
import com.adventour.web.models.Tour;
import com.adventour.web.repository.TourRepository;
import com.adventour.web.service.TourService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourServiceImpl implements TourService {
    private final TourRepository tourRepository;
    private final Mapper mapper;
    private static Logger logger = LoggerFactory.getLogger(TourController.class);

    @Autowired
    public TourServiceImpl(TourRepository tourRepository, Mapper mapper) {
        this.tourRepository = tourRepository;
        this.mapper = mapper;
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
                TourDto tourDto = mapper.mapToTourDto(tour);
                result.add(tourDto);
            }
        }
        return result;
    }

    @Override
    public List<TourDto> findAllTours() {
        List<Tour> tours = tourRepository.findAll();
        return tours.stream().map((tour) -> mapper.mapToTourDto(tour)).collect(Collectors.toList());
    }

    @Override
    public TourDto findByTourId(long tourId) {
        Tour tour = tourRepository.findById(tourId).get();
        return mapper.mapToTourDto(tour);
    }

    @Override
    public Tour saveTour(TourDto tourDto) {

        Tour tour = mapper.mapToTour(tourDto);
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
        Tour tour = mapper.mapToTour(tourDto);
        tourRepository.save(tour);
        return tour;
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


}
