package org.example.spring_quiz_application.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuizResult {
    private int id;
    private int userId;
    private int categoryId;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;

    private String categoryName;
    private int score;

    public QuizResult(int id, int userId, int categoryId,
                      LocalDateTime timeStart, LocalDateTime timeEnd) {
        this.id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.score = 0;
    }
}
