package com.example.schedule.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserRequestDto {
    @NotNull(message = "이름은 필수값입니다.")
    private String name;
    @Email(message = "이메일 형식으로 작성해 주세요.")
    private String email;
}
