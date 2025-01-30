package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class User {
    private Long id;
    @Setter
    private String name;
    @Setter
    private String email;
    private Boolean isDeleted;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    public User(String name) {
        this.name = name;
    }
}
