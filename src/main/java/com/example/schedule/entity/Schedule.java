package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {

    private Long id;
    @Setter
    private Long authorId;
    private String todo;
    private String password;
    private LocalDateTime regDate;
    @Setter
    private LocalDateTime modDate;

    public Schedule(Long authorId, String todo, String password) {
        this.authorId = authorId;
        this.todo = todo;
        this.password = password;
    }
}
