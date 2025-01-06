package org.example.spring_quiz_application.DTO;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QuizResultPageDTO {
    private String startTime;
    private String categoryName;
    private int numQuestions;
    private int score;
    private int quizResultId;
    private String userFullName;
    private int userId;
}
