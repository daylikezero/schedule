package com.example.schedule.exception;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    private ResponseEntity<ErrorResponseDto> handleException(Exception e) {
        return ErrorResponseDto.errResponseEntity(new CustomException(e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder errorMsg = new StringBuilder();
        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            errorMsg.append(error.getDefaultMessage()).append(" ");
        }
        return ErrorResponseDto.errResponseEntity(new CustomException(ErrorCode.INVALID_CONSTRAINTS, errorMsg.toString()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    private ResponseEntity<ErrorResponseDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ErrorResponseDto.errResponseEntity(new CustomException(ErrorCode.INVALID_PARAMETER, e.getMessage()));
    }
}
