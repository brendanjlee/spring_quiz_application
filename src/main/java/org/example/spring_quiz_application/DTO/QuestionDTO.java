package org.example.spring_quiz_application.DTO;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    private String categoryName;
    private String text;
    private boolean isActive;
    private int questionId;
}
