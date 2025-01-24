package com.example.schedule.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Schedule {

    private Long id;
    @Setter
    private Long authorId;
    private String author;
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

    public Schedule(Long id, Long authorId, String todo, String password, LocalDateTime modDate) {
        this.id = id;
        this.authorId = authorId;
        this.todo = todo;
        this.password = password;
        this.modDate = modDate;
    }

    public Schedule(Long id, String author, String todo, LocalDateTime regDate, LocalDateTime modDate) {
        this.id = id;
        this.author = author;
        this.todo = todo;
        this.regDate = regDate;
        this.modDate = modDate;
    }
}
