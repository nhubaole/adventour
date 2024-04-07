package com.adventour.web.service.impl;

import com.adventour.web.dto.TourDto;
import com.adventour.web.models.Tour;
import com.adventour.web.repository.TourRepository;
import com.adventour.web.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourServiceImpl implements TourService {
    private TourRepository tourRepository;

    @Autowired
    public TourServiceImpl(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    @Override
    public List<TourDto> findAllTours() {
        List<Tour> tours = tourRepository.findAll();
        return tours.stream().map((tour) -> mapToTourDto(tour)).collect(Collectors.toList());
    }

    private TourDto mapToTourDto(Tour tour) {
        return TourDto.builder()
                .id(tour.getId())
                .tourName(tour.getTourName())
                .departureLocation(tour.getDepartureLocation())
                .estimatedPrice(tour.getEstimatedPrice())
                .numberOfDays(tour.getNumberOfDays())
                .numberOfNights(tour.getNumberOfNights())
                .tourType(tour.getTourType())
                .build();
    }
}
