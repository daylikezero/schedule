package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(Schedule schedule, String author);

    List<ScheduleResponseDto> findAllSchedules(Schedule dto);

    Schedule findScheduleById(Long id);

    int updateSchedule(Long id, Schedule schedule);

    int deleteSchedule(Long id, String password);
}
