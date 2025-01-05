package org.example.spring_quiz_application.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Question {
    private int id;
    private int categoryId;
    private String text;
    private boolean isActive;

    private List<Choice> choices;

    public Question(int id, int categoryId, String text, boolean isActive) {
        this.id = id;
        this.categoryId = categoryId;
        this.text = text;
        this.isActive = isActive;
    }
}
