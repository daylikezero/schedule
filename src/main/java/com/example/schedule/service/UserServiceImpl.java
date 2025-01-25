package com.example.schedule.service;

import com.example.schedule.dto.UserRequestDto;
import com.example.schedule.dto.UserResponseDto;
import com.example.schedule.entity.User;
import com.example.schedule.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto saveUser(UserRequestDto dto) {
        User user = new User(dto.getName());
        if(StringUtils.hasText(dto.getEmail())) {
            user.setEmail(dto.getEmail());
        }
        return userRepository.saveUser(user);
    }

    @Override
    public UserResponseDto findUserById(Long id) {
        User user = userRepository.findUserById(id);
        return new UserResponseDto(user);
    }

    @Override
    public UserResponseDto findUserByName(String name) {
        User user = userRepository.findUserByName(name);
        return new UserResponseDto(user);
    }

    @Override
    public List<UserResponseDto> findAllUser() {
        return userRepository.findAllUser();
    }
}
