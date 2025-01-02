package org.example.spring_quiz_application.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuizQuestion {
    private int id;
    private int quizResultId;
    private int questionId;
    private int userChoiceId;
}
