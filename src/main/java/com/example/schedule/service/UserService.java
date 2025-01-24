package com.example.schedule.service;

import com.example.schedule.dto.UserRequestDto;
import com.example.schedule.dto.UserResponseDto;

public interface UserService {
    UserResponseDto createUser(UserRequestDto dto);

    UserResponseDto getUserByName(String name);
}
