package org.example.spring_quiz_application.DTO;

import lombok.*;
import org.example.spring_quiz_application.model.Choice;
import org.example.spring_quiz_application.model.Question;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {
    private int id;
    private int categoryId;
    private String text;
    private boolean isActive;

    private List<ChoiceDTO> choices;

//    public QuestionDTO(int id, int categoryId, String text, boolean
//    isAnswer) {
//        this.id = id;
//        this.categoryId = categoryId;
//        this.text = text;
//        this.isActive = isAnswer;
//    }

    //    public QuestionDTO(int id, int categoryId, String text, boolean
    //    isAnswer, List<Choice> choices) {
//        this.id = id;
//        this.categoryId = categoryId;
//        this.text = text;
//        this.isActive = isAnswer;
//        this.choices = choices.stream().map(ChoiceDTO::new).collect
//        (Collectors.toList());
//    }
//
    public QuestionDTO(Question question) {
        this.id = question.getId();
        this.categoryId = question.getCategory().getId();
        this.text = question.getText();
        this.isActive = question.isActive();
        this.choices =
                question.getChoices().stream().map(ChoiceDTO::new).collect(Collectors.toList());
    }
}
