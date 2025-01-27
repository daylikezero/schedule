package com.example.schedule.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@Builder
public class ErrorResponseDto {
    private String code;
    private String message;
    private String detail;

    public static ResponseEntity<ErrorResponseDto> errResponseEntity(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        String detail = e.getDetail();

        return ResponseEntity.status(e.getStatus())
                .body(ErrorResponseDto.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .detail(detail)
                        .build());
    }
}
