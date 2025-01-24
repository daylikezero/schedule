package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    public User(String name) {
        this.name = name;
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
