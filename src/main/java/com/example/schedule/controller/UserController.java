package com.example.schedule.controller;

import com.example.schedule.dto.UserRequestDto;
import com.example.schedule.dto.UserResponseDto;
import com.example.schedule.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> saveUser(@RequestBody UserRequestDto dto) {
        return new ResponseEntity<>(userService.saveUser(dto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<UserResponseDto> findUserByName(@RequestParam String name) {
        return new ResponseEntity<>(userService.findUserByName(name), HttpStatus.OK);
    }
}
