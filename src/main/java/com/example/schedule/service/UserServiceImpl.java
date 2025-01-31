package com.example.schedule.service;

import com.example.schedule.dto.UserRequestDto;
import com.example.schedule.dto.UserResponseDto;
import com.example.schedule.entity.User;
import com.example.schedule.exception.CustomException;
import com.example.schedule.exception.ErrorCode;
import com.example.schedule.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        if (StringUtils.hasText(dto.getEmail())) {
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
    public List<UserResponseDto> findAllUser() {
        return userRepository.findAllUser();
    }

    @Transactional
    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto dto) {
        User user = validUser(id);
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        int updatedRow = userRepository.updateUser(id, user);

        if (updatedRow == 0) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        return new UserResponseDto(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        validUser(id);

        int deleteRow = userRepository.deleteUser(id);

        if (deleteRow == 0) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
    }

    private User validUser(Long id) {
        User user = userRepository.findUserById(id);
        if (user.getIsDeleted()) {
            throw new CustomException(ErrorCode.ENTITY_DELETED, String.valueOf(id));
        }
        return user;
    }
}
