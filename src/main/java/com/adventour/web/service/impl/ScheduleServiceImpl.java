package com.adventour.web.service.impl;

import com.adventour.web.dto.ScheduleDto;
import com.adventour.web.models.Schedule;
import com.adventour.web.repository.ScheduleRepository;
import com.adventour.web.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;

public class ScheduleServiceImpl implements ScheduleService {
    private ScheduleRepository scheduleRepository;
    @Autowired
    public ScheduleServiceImpl (ScheduleRepository scheduleRepository){
        this.scheduleRepository = scheduleRepository;
    }

    private ScheduleDto mapToScheduleDto(Schedule schedule){
        return ScheduleDto.builder()
                .id(schedule.getId())
                .dayOfSchedule(schedule.getDayOfSchedule())

                .build();
    }


}
