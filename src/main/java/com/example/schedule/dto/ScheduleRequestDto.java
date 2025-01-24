package com.example.schedule.dto;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
public class ScheduleRequestDto {

    private String author;
    private String todo;
    private String password;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date modDate;

}
