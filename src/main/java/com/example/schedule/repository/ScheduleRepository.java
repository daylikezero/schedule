package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {
    ScheduleResponseDto createSchedule(Schedule schedule, String author);

    List<ScheduleResponseDto> findAllSchedules(Schedule dto);
}
