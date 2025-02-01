package com.example.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Schema(description = "할일 요청 DTO")
public class ScheduleRequestDto {

    @NotNull(message = "작성자 ID는 필수값입니다.")
    @Schema(description = "작성자 ID", example = "1")
    private Long authorId;
    @Schema(description = "할일", example = "내용")
    @NotNull(message = "할일은 필수값입니다.")
    @Size(max = 200, message = "할일은 최대 200자 이하로 입력해 주세요.")
    private String todo;
    @Schema(description = "비밀번호", example = "비밀번호")
    @NotNull(message = "비밀번호는 필수값입니다.")
    private String password;
    @Schema(description = "수정일", example = "2025-01-01", hidden = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date modDate;

}
