package com.example.schedule.dto;

import com.example.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {

    private Long id;
    private String author;
    private String todo;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.author = schedule.getAuthor();
        this.todo = schedule.getTodo();
        this.regDate = schedule.getRegDate();
        this.modDate = schedule.getModDate();
    }

    public ScheduleResponseDto(Long id, String author, String todo, LocalDateTime regDate, LocalDateTime modDate) {
        this.id = id;
        this.author = author;
        this.todo = todo;
        this.regDate = regDate;
        this.modDate = modDate;
    }
}
