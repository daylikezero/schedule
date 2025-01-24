package com.example.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {

    private Long id;
    private String author;
    private String todo;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    public ScheduleResponseDto(Long id, String author, String todo, LocalDateTime regDate, LocalDateTime modDate) {
        this.id = id;
        this.author = author;
        this.todo = todo;
        this.regDate = regDate;
        this.modDate = modDate;
    }
}
