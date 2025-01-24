package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.User;
import com.example.schedule.repository.ScheduleRepository;
import com.example.schedule.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, UserRepository userRepository) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ScheduleResponseDto createSchedule(ScheduleRequestDto dto) {
        // 일정 작성 시 사용자 DB 에서 작성자 id 조회
        User user = userRepository.getUserByName(dto.getAuthor());
        // TODO 패스워드 암호화
        Schedule schedule = new Schedule(user.getId(), dto.getTodo(), dto.getPassword());

        return scheduleRepository.createSchedule(schedule, dto.getAuthor());
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(ScheduleRequestDto dto) {

        Schedule schedule = new Schedule();

        if (StringUtils.hasText(dto.getAuthor())) {
            User user = userRepository.getUserByName(dto.getAuthor());
            schedule.setAuthorId(user.getId());
        }
        if (dto.getDate() != null) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(dto.getDate().toInstant(), ZoneId.systemDefault());
            schedule.setModDate(localDateTime);
        }
        return scheduleRepository.findAllSchedules(schedule);
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findScheduleById(id);
        return new ScheduleResponseDto(schedule);
    }
}
