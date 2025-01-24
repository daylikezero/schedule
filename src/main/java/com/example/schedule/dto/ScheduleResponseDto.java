package com.example.schedule.dto;

import com.example.schedule.entity.Schedule;
import com.example.schedule.util.LocalDateTimeUtils;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {

    private Long id;
    private String author;
    private String todo;
    private String regDate;
    private String modDate;

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
