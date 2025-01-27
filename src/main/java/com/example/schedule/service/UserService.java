package com.example.schedule.service;

import com.example.schedule.dto.UserRequestDto;
import com.example.schedule.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto saveUser(UserRequestDto dto);

    UserResponseDto findUserById(Long id);

    List<UserResponseDto> findAllUser();
}
