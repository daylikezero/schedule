package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.dto.UpdateScheduleRequestDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.User;
import com.example.schedule.exception.CustomException;
import com.example.schedule.exception.ErrorCode;
import com.example.schedule.repository.ScheduleRepository;
import com.example.schedule.repository.UserRepository;
import com.example.schedule.util.EmptyTool;
import com.example.schedule.util.LocalDateTimeUtils;
import com.example.schedule.util.Paging;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
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
        User user = userRepository.findUserById(dto.getAuthorId());

        Schedule schedule = new Schedule(user.getId(), dto.getTodo(), dto.getPassword());

        return scheduleRepository.saveSchedule(schedule, user.getName());
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(ScheduleRequestDto dto, Integer pageNo, Integer size) {
        Schedule schedule = new Schedule();

        if (EmptyTool.notEmpty(dto.getAuthorId())) {
            try {
                User user = userRepository.findUserById(dto.getAuthorId());
                schedule.setAuthorId(user.getId());
            } catch (CustomException e) {
                if (e.getErrorCode() == ErrorCode.USER_NOT_FOUND) {
                    return Collections.emptyList();
                }
            }
        }

        if (EmptyTool.notEmpty(dto.getModDate())) {
            LocalDateTime localDateTime = LocalDateTimeUtils.dateToLocalDateTime(dto.getModDate());
            schedule.setModDate(localDateTime);
        }

        return scheduleRepository.findAllSchedules(schedule, getPaging(pageNo, size));
    }

    private static Paging getPaging(Integer pageNo, Integer size) {
        Paging paging = null;
        if (EmptyTool.notEmpty(pageNo) || EmptyTool.notEmpty(size)) {
            int pageNumber = pageNo == null ? 1 : pageNo;
            int pageSize = size == null ? 10 : size;
            paging = new Paging(pageNumber, pageSize);
        }
        return paging;
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findScheduleById(id);
        if (schedule.getIsDeleted()) {
            throw new CustomException(ErrorCode.ENTITY_DELETED, String.valueOf(id));
        }
        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, UpdateScheduleRequestDto dto) {
        Schedule schedule = validSchedule(id, dto.getPassword());
        if (EmptyTool.notEmpty(dto.getTodo())) {
            schedule.setTodo(dto.getTodo());
        }

        if (EmptyTool.notEmpty(dto.getAuthorId())) {
            User user = userRepository.findUserById(dto.getAuthorId());
            schedule.setAuthorId(user.getId());
        }

        int updatedRow = scheduleRepository.updateSchedule(id, schedule);
        if (updatedRow == 0) {
            throw new CustomException(ErrorCode.SCHEDULE_NOT_FOUND);
        }

        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    @Override
    public void deleteSchedule(Long id, UpdateScheduleRequestDto dto) {
        validSchedule(id, dto.getPassword());
        int deleteRow = scheduleRepository.deleteSchedule(id);
        if (deleteRow == 0) {
            throw new CustomException(ErrorCode.SCHEDULE_NOT_FOUND);
        }
    }

    private Schedule validSchedule(Long id, String password) {
        if (!StringUtils.hasText(password)) {
            throw new CustomException(ErrorCode.INVALID_PARAMETER);
        }
        Schedule schedule = scheduleRepository.findScheduleById(id);
        if (schedule.getIsDeleted()) {
            throw new CustomException(ErrorCode.ENTITY_DELETED, String.valueOf(id));
        }
        if (!password.equals(schedule.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_INCORRECT);
        }
        return schedule;
    }
}
