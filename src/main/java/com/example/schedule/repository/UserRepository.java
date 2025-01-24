package com.example.schedule.repository;

import com.example.schedule.dto.UserResponseDto;
import com.example.schedule.entity.User;

public interface UserRepository {
    UserResponseDto saveUser(User user);

    User findUserByName(String name);
}
