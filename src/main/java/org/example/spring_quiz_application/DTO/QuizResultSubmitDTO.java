package org.example.spring_quiz_application.DTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QuizResultSubmitDTO {
    private LocalDateTime startTime;
    private int categoryId;
    Map<Integer, Integer> questionToChoiceMap;
}
