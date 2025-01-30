package com.example.schedule.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
public class ScheduleRequestDto {

    private Long authorId;
    private String author;
    @NotNull
    @Size(max = 200, message = "할일은 최대 200자 이하로 입력해 주세요.")
    private String todo;
    @NotNull
    private String password;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date modDate;

}
