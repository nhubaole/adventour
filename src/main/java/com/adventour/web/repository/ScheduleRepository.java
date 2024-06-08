package com.adventour.web.repository;
import com.adventour.web.models.Location;
import com.adventour.web.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Optional<Schedule> findById(Long id);

    List<Schedule> findByStartLocation(Location location);
    List<Schedule> findByEndLocation(Location location);
}