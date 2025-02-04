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
    @Setter
    private String todo;
    private String password;
    private Boolean isDeleted;
    private LocalDateTime regDate;
    @Setter
    private LocalDateTime modDate;

    public Schedule(Long authorId, String todo, String password) {
        this.authorId = authorId;
        this.todo = todo;
        this.password = password;
    }

    public Schedule(Long id, Long authorId, String author, String todo, String password,
                    Boolean isDeleted, LocalDateTime regDate, LocalDateTime modDate) {
        this(authorId, todo, password);
        this.id = id;
        this.author = author;
        this.isDeleted = isDeleted;
        this.regDate = regDate;
        this.modDate = modDate;
    }
}
