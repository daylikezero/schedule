package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.User;
import com.example.schedule.exception.CustomException;
import com.example.schedule.exception.ErrorCode;
import com.example.schedule.repository.ScheduleRepository;
import com.example.schedule.repository.UserRepository;
import com.example.schedule.util.EmptyTool;
import com.example.schedule.util.Paging;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

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
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
        User user = userRepository.findUserById(dto.getAuthorId());

        // TODO 패스워드 암호화
        Schedule schedule = new Schedule(user.getId(), dto.getTodo(), dto.getPassword());

        return scheduleRepository.saveSchedule(schedule, user.getName());
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(ScheduleRequestDto dto, Integer pageNo, Integer size) {
        Schedule schedule = new Schedule();
        Paging paging = null;
        if (EmptyTool.notEmpty(dto.getAuthorId())) {
            User user = userRepository.findUserById(dto.getAuthorId());
            schedule.setAuthorId(user.getId());
        }

        if (EmptyTool.notEmpty(dto.getModDate())) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(dto.getModDate().toInstant(), ZoneId.systemDefault());
            schedule.setModDate(localDateTime);
        }

        if (EmptyTool.notEmpty(pageNo) || EmptyTool.notEmpty(size)) {
            int pageNumber = pageNo == null ? 1 : pageNo;
            int pageSize = size == null ? 10 : size;
            paging = new Paging(pageNumber, pageSize);
        }

        return scheduleRepository.findAllSchedules(schedule, paging);
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findScheduleById(id);
        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto dto) {

        if (EmptyTool.empty(dto.getAuthorId()) || !StringUtils.hasText(dto.getPassword())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_PARAMETER);
        }
        User user = userRepository.findUserById(dto.getAuthorId());
        // TODO 패스워드 암호화
        Schedule schedule = new Schedule(id, user.getId(), dto.getTodo(), dto.getPassword());
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
