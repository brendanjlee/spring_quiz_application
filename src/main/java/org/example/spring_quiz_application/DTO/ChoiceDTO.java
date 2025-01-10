package org.example.spring_quiz_application.DTO;

import lombok.*;
import org.example.spring_quiz_application.model.Choice;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChoiceDTO {
    private int id;
    private int questionId;
    private String text;
    private boolean isAnswer;
    private boolean isUserAnswer = false;

    public ChoiceDTO(Choice choice) {
        this.id = choice.getId();
        this.questionId = choice.getQuestion().getId();
        this.text = choice.getText();
        this.isAnswer = choice.isAnswer();
    }
}
