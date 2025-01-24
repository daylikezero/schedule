package com.example.schedule.dto;

import com.example.schedule.entity.User;
import com.example.schedule.util.LocalDateTimeUtils;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private String regDate;
    private String modDate;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.regDate = LocalDateTimeUtils.formatToIsoLocalDate(user.getRegDate());
        this.modDate = LocalDateTimeUtils.formatToIsoLocalDate(user.getModDate());
    }

    public UserResponseDto(Long id, String name, String email, LocalDateTime regDate, LocalDateTime modDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.regDate = LocalDateTimeUtils.formatToIsoLocalDate(regDate);
        this.modDate = LocalDateTimeUtils.formatToIsoLocalDate(modDate);
    }
}
