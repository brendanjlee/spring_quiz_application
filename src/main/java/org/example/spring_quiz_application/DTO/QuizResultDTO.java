package org.example.spring_quiz_application.DTO;

import lombok.*;
import org.example.spring_quiz_application.model.*;

import java.time.LocalDateTime;
import java.util.List;

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


    // computed
    private int result;
    private List<QuestionDTO> questions;
    private int numQuestions;
    private String userFullName;

    public QuizResultDTO(int id, int userId, String categoryName,
                         LocalDateTime timeStart, LocalDateTime timeEnd) {
        this.id = id;
        this.userId = userId;
        this.categoryName = categoryName;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public QuizResultDTO(QuizResult quizResult) {
        this.id = quizResult.getId();
        this.userId = quizResult.getUser().getId();
        this.categoryName = quizResult.getCategory().getName();
        this.timeStart = quizResult.getTimeStart();
        this.timeEnd = quizResult.getTimeEnd();
    }
}
