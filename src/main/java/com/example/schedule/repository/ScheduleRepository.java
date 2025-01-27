package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.util.Paging;

import java.util.List;

public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(Schedule schedule, String author);

    List<ScheduleResponseDto> findAllSchedules(Schedule dto, Paging paging);

    Schedule findScheduleById(Long id);

    int updateSchedule(Long id, Schedule schedule);

    int deleteSchedule(Long id, String password);
}
