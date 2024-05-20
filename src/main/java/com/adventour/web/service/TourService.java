package com.adventour.web.service;

import com.adventour.web.dto.ScheduleDto;
import com.adventour.web.dto.TourDto;
import com.adventour.web.models.Schedule;
import com.adventour.web.models.Tour;

import java.util.List;

public interface TourService {
    List<TourDto> searchTour(String keyword);
    List<TourDto> findAllTours();
    TourDto findByTourId(long tourId);
    Tour saveTour(TourDto tour);
    Tour findByJustTourId(long tourId);

    List<ScheduleDto> getTourSchedule(long tourId);
    Tour editTourDetail(TourDto tourDto);
    Tour editTourSchedule(ScheduleDto scheduleDto);
    boolean deleteTour(long tourId);
}
