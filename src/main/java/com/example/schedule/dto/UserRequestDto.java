package com.example.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserRequestDto {
    @Schema(description = "이름", example = "이름")
    @NotNull(message = "이름은 필수값입니다.")
    private String name;
    @Schema(description = "이메일", example = "a@a.com")
    @Email(message = "이메일 형식으로 작성해 주세요.")
    private String email;
}
