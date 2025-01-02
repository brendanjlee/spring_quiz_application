package org.example.spring_quiz_application.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Choice {
    private int id;
    private int questionId;
    private String text;
    private boolean isAnswer;
}
