package org.example.spring_quiz_application.DTO;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QuestionSubmitDTO {
    private int categoryId;
    private String text;
    private Map<String, String> paramMap;

    // for updating question
    private int questionId;

    public QuestionSubmitDTO(int categoryId, String text, Map<String, String> paramMap) {
        this.categoryId = categoryId;
        this.text = text;
        this.paramMap = paramMap;
    }

    public List<ChoiceDTO> extractChoices() {
        List<ChoiceDTO> choices = new ArrayList<>();

        Pattern chociePattern = Pattern.compile("choices\\[(\\d+)]\\.(text|isAnswer)");
        // text
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Matcher matcher = chociePattern.matcher(key);
            if (matcher.matches()) {
                int index = Integer.parseInt(matcher.group(1));
                String field = matcher.group(2);


                if (field.equals("text")) {
                    ChoiceDTO choice = new ChoiceDTO();
                    choice.setText(value);
                    choices.add(choice);
                }
            }
        }

        // answer
        int index = 0;
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Matcher matcher = chociePattern.matcher(key);
            if (matcher.matches()) {

                String field = matcher.group(2);

                if (field.equals("isAnswer")) {
                    index--;
                }

                ChoiceDTO choice = choices.get(index++);

                if (field.equals("isAnswer")) {
                    choice.setAnswer(true);
                }
            }
        }

        return choices;
    }
}
