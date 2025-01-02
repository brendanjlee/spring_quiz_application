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
}
