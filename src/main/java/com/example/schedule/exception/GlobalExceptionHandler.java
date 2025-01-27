package com.example.schedule.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    private ResponseEntity<ErrorResponseDto> handleException(Exception e) {
        return ErrorResponseDto.errResponseEntity(new CustomException(e));
    }
}
