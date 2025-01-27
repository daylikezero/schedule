package com.example.schedule.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
    private final HttpStatus status;
    private final ErrorCode errorCode;
    private final String detail;

    public CustomException(HttpStatus status, ErrorCode errorCode) {
        this.status = status;
        this.errorCode = errorCode;
        this.detail = "";
    }

    public CustomException(HttpStatus status, ErrorCode errorCode, String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.detail = message;
    }

    public CustomException(Exception exception) {
        if (exception.getClass() == CustomException.class) {
            CustomException customException = (CustomException) exception;
            this.status = customException.getStatus();
            this.errorCode = customException.getErrorCode();
            this.detail = customException.getDetail();
        } else {
            this.status = HttpStatus.INTERNAL_SERVER_ERROR;
            this.errorCode = ErrorCode.UNKNOWN;
            this.detail = "알 수 없는 오류 발생, 서버팀에 문의 바랍니다.";
        }
    }
}
