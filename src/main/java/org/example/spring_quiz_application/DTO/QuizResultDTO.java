package org.example.spring_quiz_application.DTO;

import lombok.*;
import org.example.spring_quiz_application.model.QuizResult;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QuizResultDTO {
    private int id;
    private int userId;
    private String categoryName;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;

    public QuizResultDTO(QuizResult quizResult) {
        this.id = quizResult.getId();
        this.userId = quizResult.getUser().getId();
        this.categoryName = quizResult.getCategory().getName();
        this.timeStart = quizResult.getTimeStart();
        this.timeEnd = quizResult.getTimeEnd();
    }
}
