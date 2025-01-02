package org.example.spring_quiz_application.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Question {
    private int id;
    private int categoryId;
    private String text;
    private boolean isActive;
}
