package com.example.schedule.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@Builder
public class ErrorDto {
    private String code;
    private String message;
    private String detail;

    public static ResponseEntity<ErrorDto> errResponseEntity(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        String detail = e.getDetail();

        return ResponseEntity.status(e.getStatus())
                .body(ErrorDto.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .detail(detail)
                        .build());
    }
}
