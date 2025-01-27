package com.example.schedule.repository;

import com.example.schedule.dto.UserResponseDto;
import com.example.schedule.entity.User;

import java.util.List;

public interface UserRepository {
    UserResponseDto saveUser(User user);

    User findUserById(Long id);

    List<UserResponseDto> findAllUser();
}
