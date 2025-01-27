package com.example.schedule.dto;

import com.example.schedule.entity.Schedule;
import com.example.schedule.util.LocalDateTimeUtils;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {

    private final Long id;
    private final String author;
    private final String todo;
    private final String regDate;
    private final String modDate;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.author = schedule.getAuthor();
        this.todo = schedule.getTodo();
        this.regDate = LocalDateTimeUtils.formatToIsoLocalDate(schedule.getRegDate());
        this.modDate = LocalDateTimeUtils.formatToIsoLocalDate(schedule.getRegDate());
    }

    public ScheduleResponseDto(Long id, String author, String todo, LocalDateTime regDate, LocalDateTime modDate) {
        this.id = id;
        this.author = author;
        this.todo = todo;
        this.regDate = LocalDateTimeUtils.formatToIsoLocalDate(regDate);
        this.modDate = LocalDateTimeUtils.formatToIsoLocalDate(modDate);
    }
}
