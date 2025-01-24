package com.example.schedule.service;

import com.example.schedule.dto.UserRequestDto;
import com.example.schedule.dto.UserResponseDto;
import com.example.schedule.entity.User;
import com.example.schedule.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto createUser(UserRequestDto dto) {
        User user = new User(dto.getName(), dto.getEmail());
        return userRepository.createUser(user);
    }

    @Override
    public UserResponseDto getUserByName(String name) {
        User user = userRepository.getUserByName(name);
        return new UserResponseDto(user);
    }
}
