package org.example.spring_quiz_application.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Choice {
    private int id;
    private int questionId;
    private String text;
    private boolean isAnswer;

    private boolean isUserAnswer;

    public Choice(int id, int questionId, String text, boolean isAnswer) {
        this.id = id;
        this.questionId = questionId;
        this.text = text;
        this.isAnswer = isAnswer;
        this.isUserAnswer = false;
    }
}
