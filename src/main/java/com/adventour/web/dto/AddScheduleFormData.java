package com.adventour.web.dto;


public class AddScheduleFormData {
    public ScheduleDto schedule;
    public TourDto tour;

    public ScheduleDto getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleDto schedule) {
        this.schedule = schedule;
    }

    public TourDto getTour() {
        return tour;
    }

    public void setTour(TourDto tour) {
        this.tour = tour;
    }

    @Override
    public String toString() {
        return "AddScheduleFormData{" +
                "schedule=" + schedule +
                ", tour=" + tour +
                '}';
    }
}
