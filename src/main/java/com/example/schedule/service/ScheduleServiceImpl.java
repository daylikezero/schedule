package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.User;
import com.example.schedule.repository.ScheduleRepository;
import com.example.schedule.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
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
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
        // 일정 작성 시 사용자 DB 에서 작성자 id 조회
        User user = userRepository.findUserByName(dto.getAuthor());
        // TODO 패스워드 암호화
        Schedule schedule = new Schedule(user.getId(), dto.getTodo(), dto.getPassword());

        return scheduleRepository.saveSchedule(schedule, dto.getAuthor());
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(ScheduleRequestDto dto) {

        Schedule schedule = new Schedule();

        if (StringUtils.hasText(dto.getAuthor())) {
            try {
                User user = userRepository.findUserByName(dto.getAuthor());
                schedule.setAuthorId(user.getId());
            } catch (ResponseStatusException e) {
                return Collections.emptyList();
            }
        }
        if (dto.getModDate() != null) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(dto.getModDate().toInstant(), ZoneId.systemDefault());
            schedule.setModDate(localDateTime);
        }
        return scheduleRepository.findAllSchedules(schedule);
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findScheduleById(id);
        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto dto) {

        if (!StringUtils.hasText(dto.getAuthor()) || !StringUtils.hasText(dto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "author or password is missing");
        }
        User user = userRepository.findUserByName(dto.getAuthor());
        // TODO 패스워드 암호화
        Schedule schedule = new Schedule(id, user.getId(), dto.getTodo(), dto.getPassword(), LocalDateTime.now());
        int updatedRow = scheduleRepository.updateSchedule(id, schedule);
        // TODO 수정 실패: 패스워드 불일치 조건 분기
        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found");
        }

        schedule = scheduleRepository.findScheduleById(id);

        return new ScheduleResponseDto(schedule);
    }

    @Override
    public void deleteSchedule(Long id, ScheduleRequestDto dto) {
        // TODO 패스워드 암호화
        int deleteRow = scheduleRepository.deleteSchedule(id, dto.getPassword());
        // TODO 삭제 실패: 패스워드 불일치 조건 분기
        if (deleteRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found");
        }
    }
}
