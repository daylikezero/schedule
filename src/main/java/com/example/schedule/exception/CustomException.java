package com.example.schedule.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Getter
@Slf4j
public class CustomException extends RuntimeException {
    private final HttpStatus status;
    private final ErrorCode errorCode;
    private final String detail;

    public CustomException(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.errorCode = errorCode;
        this.detail = "";
    }

    public CustomException(ErrorCode errorCode, String detail) {
        this.status = errorCode.getStatus();
        this.errorCode = errorCode;
        this.detail = detail;
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
            this.detail = "알 수 없는 오류 발생, 개발팀에 문의 바랍니다.";
            log.error(exception.getMessage(), exception);
        }
    }
}
