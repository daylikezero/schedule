package com.example.schedule.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "ER-001", "파라미터 값을 확인해주세요."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "ER-002", "존재하지 않는 유저 ID 입니다."),
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "ER-003", "존재하지 않는 일정 ID 입니다."),
    PASSWORD_INCORRECT(HttpStatus.BAD_REQUEST, "ER-004", "비밀번호가 일치하지 않습니다."),
    ENTITY_DELETED(HttpStatus.BAD_REQUEST, "ER-005", "이미 삭제된 정보입니다."),
    UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR, "ER-999", "알 수 없는 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
